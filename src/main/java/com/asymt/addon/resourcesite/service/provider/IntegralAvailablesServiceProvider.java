package com.asymt.addon.resourcesite.service.provider;

import com.asymt.addon.resourcesite.AddonDb;
import com.asymt.addon.resourcesite.model.IntegralAvailables;
import com.asymt.addon.resourcesite.service.IntegralAvailablesService;
import com.asymt.addon.resourcesite.service.IntegralDetailsService;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import io.jboot.aop.annotation.Bean;
import io.jboot.aop.annotation.Transactional;
import io.jboot.db.model.Columns;
import io.jpress.commons.service.JPressServiceBase;

import java.util.Date;
import java.util.List;

@Bean
public class IntegralAvailablesServiceProvider extends JPressServiceBase<IntegralAvailables> implements IntegralAvailablesService {

    @Inject
    IntegralDetailsService integralDetailsService;
    @Override
    public void updateUserIntegral(Long userId){
        Integer sumIntegral= AddonDb.getDbPro().template("integral.queryAvailablesIntegralSum",userId).queryInt();
        String integralTableName="user_integral";
        String userIntegralPrimaryKey="user_id";
        if(sumIntegral==null){
            Db.deleteById(integralTableName, userIntegralPrimaryKey,userId);
        }else {
            Record userIntegral=Db.findById(integralTableName,userIntegralPrimaryKey,userId);
            if(userIntegral==null){
                Db.save(integralTableName,new Record().set(userIntegralPrimaryKey,userId).set("integral",sumIntegral));
            }else {
                Db.update(integralTableName,userIntegralPrimaryKey,userIntegral);
            }
        }
    }


    @Transactional
    @Override
    public void expireIntegral(){
        Columns columns = Columns.create().lt("expire", new Date());
        List<IntegralAvailables> expireList=findListByColumns(columns);
        if(expireList!=null&&expireList.size()>0){
            DAO.deleteByColumns(columns);
            integralDetailsService.addExpireIntegralDetails(expireList);
            String sql=AddonDb.getDbPro().getSql("integral.updateAllUserIntegral");
            Db.update(sql);
        }
    }
}
