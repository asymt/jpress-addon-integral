package com.asymt.addon.resourcesite.service.provider;

import com.asymt.addon.resourcesite.AddonDb;
import com.asymt.addon.resourcesite.model.IntegralAvailables;
import com.asymt.addon.resourcesite.service.IntegralAvailablesService;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import io.jboot.aop.annotation.Bean;
import com.asymt.addon.resourcesite.service.IntegralDetailsService;
import com.asymt.addon.resourcesite.model.IntegralDetails;
import io.jboot.aop.annotation.Transactional;
import io.jboot.db.model.Columns;
import io.jboot.utils.DateUtil;
import io.jpress.commons.service.JPressServiceBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Bean
public class IntegralDetailsServiceProvider extends JPressServiceBase<IntegralDetails> implements IntegralDetailsService {

    @Inject
    IntegralAvailablesService integralAvailablesService;
    @Transactional
    @Override
    public Object add(IntegralDetails integralDetails){
        // 转换出一个可用积分对象然后保存
        IntegralAvailables integralAvailables = toIntegralAvailables(integralDetails);
        integralAvailablesService.save(integralAvailables);
        // 统计用户总积分
        integralAvailablesService.updateUserIntegral(integralDetails.getUserId());
        return save(integralDetails);
    }

    @Transactional
    @Override
    public Object add(Integer userId, Integer integral, String remark){
        Date currentDate=new Date();
        IntegralDetails integralDetails=new IntegralDetails();
        integralDetails.setExpire(DateUtil.addYears(currentDate,1));
        integralDetails.setRemark(remark);
        integralDetails.setType(IntegralDetails.ADD_TYPE);
        integralDetails.setCreated(currentDate);
        integralDetails.setIntegral(integral);
        integralDetails.setUserId(userId);
        return add(integralDetails);
    }

    @Override
    public boolean addExpireIntegralDetails(List<IntegralAvailables> expireIntegrals){
        List<IntegralDetails> expireDetails=toIntegralDetails(expireIntegrals,IntegralDetails.EXPIRE_TYPE,"积分过期");
        return Db.batchSave(expireDetails,expireDetails.size()).length==expireDetails.size();
    }
    private IntegralAvailables toIntegralAvailables(IntegralDetails integralDetails){
        IntegralAvailables integralAvailables=new IntegralAvailables();
        integralAvailables.setIntegral(integralDetails.getIntegral());
        integralAvailables.setCreated(integralDetails.getCreated());
        integralAvailables.setExpire(integralDetails.getExpire());
        integralAvailables.setUserId(integralDetails.getUserId());
        return integralAvailables;
    }

    private IntegralDetails toIntegralDetails(IntegralAvailables integralAvailables,Integer type,String remark){
        IntegralDetails integralDetails=new IntegralDetails();
        integralDetails.setCreated(new Date());
        integralDetails.setRemark(remark);
        integralDetails.setType(type);
        integralDetails.setUserId(integralAvailables.getUserId());
        integralDetails.setIntegral(integralAvailables.getIntegral());
        integralDetails.setExpire(integralAvailables.getExpire());
        return integralDetails;
    }

    private List<IntegralDetails> toIntegralDetails(List<IntegralAvailables> integralAvailablesList,Integer type,String remark){
        List<IntegralDetails> result=new ArrayList<>();
        if(integralAvailablesList==null||integralAvailablesList.size()==0){
            return result;
        }
        if(type==null||(type!=IntegralDetails.ADD_TYPE&&type!=IntegralDetails.CONSUME_TYPE&&type!=IntegralDetails.EXPIRE_TYPE)){
            type=IntegralDetails.ADD_TYPE;
        }
        for (IntegralAvailables integralAvailables : integralAvailablesList) {
            result.add(toIntegralDetails(integralAvailables,type,remark));
        }
        return result;
    }

    /**
     * 1、先判断用户总积分是否足够消费，如果不够则掘异常
     * 2、如果足够，则根据过期时间升序进行分页查询第一页（前10条）的可用积分，
     * 然后对查询出的10条可用积分记录值进行累加，直到找到第一条累加后大于所需积分时的可用积分记录，
     * 将该条积分记录之前的所有记录暂存入待消费列表中，然后将找到的这条积分记录拆分为两条记录，
     * 一条记录的积分值为补齐消耗所需，并存入待消费列表，另一条为拆分后剩余的积分，更新到可用积分表中。
     * 最后将待消费列表中的所有记录从可用积分表中删除，并且在积分详细表中添加对应的消费记录。
     * 3、如果遍历完第一页的所有积分记录，依然没有累加到需要消耗的积分值，将第一页的所有可用积分记录
     * 加入待消费积分并从可用积分表中删除并且在积分详细表中添加对应的消费记录，然后递归第二步的操作
     * 待消费列表进行消费
     * @param userId
     * @param integral
     * @param remark
     */
    @Override
    @Transactional
    public void consumeIntegral(Integer userId, Integer integral, String remark){
        // 先判断用户总积分是否足够消费
        Integer userIntegral= AddonDb.use().template("integral.queryUserIntegral",userId).queryInt();
        if(userIntegral==null || userIntegral<integral){
            throw new RuntimeException(String.format("您当前剩余积分%s，小于当前操作所需积分%s",userIntegral,integral));
        }
        realConsumeIntegral(userId, integral, remark);
    }

    private void realConsumeIntegral(Integer userId, Integer integral, String remark) {
        Columns columns=Columns.create("user_id", userId);
        List<IntegralAvailables> consumeList = new ArrayList<>();
        int consumeSum=0;
        Page<IntegralAvailables> integralList = integralAvailablesService.paginateByColumns(1,10,columns,"expire asc");
        for (IntegralAvailables integralAvailables : integralList.getList()) {
            consumeSum+=integralAvailables.getIntegral();
            if(consumeSum<= integral){
                consumeList.add(integralAvailables);
            }else {
                //计算多余的积分
                int redundantIntegral=consumeSum - integral;
                //如果多余的积分比当前可用积分记录的值小，则将该条可用积分拆分成两份，一份用于消费，一份继续存入可用积分表
                if(redundantIntegral<integralAvailables.getIntegral()) {
                    int partOfConsumeIntegral=integralAvailables.getIntegral()-redundantIntegral;
                    integralAvailables.setIntegral(redundantIntegral);
                    integralAvailablesService.save(integralAvailables);
                    integralAvailables.setIntegral(partOfConsumeIntegral);
                    integralAvailables.setId(-1);
                }
                //根据消费的可用积分生成积分消费明细
                if(consumeList.size()>0) {
                    integralAvailablesService.batchDeleteByIds(consumeList.stream().mapToInt(IntegralAvailables::getId).toArray());
                    List<IntegralDetails> consumeDetails=toIntegralDetails(consumeList,IntegralDetails.CONSUME_TYPE, remark);
                    if(consumeDetails.size()>0){
                        Db.batchSave(consumeDetails,consumeDetails.size());
                    }
                }
                return;
            }
        }
        //如果一页的可用积分都不够消费，则递归继续消费
        if(consumeSum<integral){
            realConsumeIntegral(userId,integral-consumeSum,remark);
        }
    }
}
