package com.asymt.addon.resourcesite.service.provider;

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
import io.jpress.commons.service.JPressServiceBase;

import java.util.ArrayList;
import java.util.List;

@Bean
public class IntegralDetailsServiceProvider extends JPressServiceBase<IntegralDetails> implements IntegralDetailsService {

    @Inject
    IntegralAvailablesService integralAvailablesService;
    @Override
    public Object add(IntegralDetails integralDetails){
        // 转换出一个可用积分对象然后保存
        IntegralAvailables integralAvailables = toIntegralAvailables(integralDetails);
        integralAvailablesService.save(integralAvailables);
        // 统计用户总积分
        integralAvailablesService.countIntegral(integralDetails.getUserId());
        return save(integralDetails);
    }


    private IntegralAvailables toIntegralAvailables(IntegralDetails integralDetails){
        IntegralAvailables integralAvailables=new IntegralAvailables();
        integralAvailables.setIntegral(integralDetails.getIntegral());
        integralAvailables.setCreated(integralDetails.getCreated());
        integralAvailables.setExpire(integralDetails.getExpire());
        integralAvailables.setUserId(integralDetails.getUserId());
        return integralAvailables;
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
    @Transactional
    public void consumeIntegral(Integer userId,Integer integral,String remark){
        // 先判断用户总积分是否足够消费
        Integer userIntegral=Db.template("integral.queryUserIntegral",userId).queryInt();
        if(userIntegral==null || userIntegral<integral){
            throw new RuntimeException(String.format("您当前剩余积分%s，小于当前操作所需积分%s",userIntegral,integral));
        }
        Columns columns=Columns.create("user_id",userId);
        List<IntegralAvailables> deleteList = new ArrayList<>();
        int consumeSum=0;
        Page<IntegralAvailables> integralList = integralAvailablesService.paginateByColumns(1,10,columns,"expire asc");
        for (IntegralAvailables integralAvailables : integralList.getList()) {
            consumeSum+=integralAvailables.getIntegral();
            if(consumeSum<=integral){
                deleteList.add(integralAvailables);
            }else {
                //计算多余的积分
                int redundantIntegral=consumeSum-integral;
                if(redundantIntegral<integralAvailables.getIntegral()) {
                    int partOfConsumeIntegral=integralAvailables.getIntegral()-redundantIntegral;
                    integralAvailables.setIntegral(redundantIntegral);
                    integralAvailablesService.save(integralAvailables);
                    integralAvailables.setIntegral(partOfConsumeIntegral);
                    integralAvailables.setId(null);
                }

                return;
            }
        }
    }
}
