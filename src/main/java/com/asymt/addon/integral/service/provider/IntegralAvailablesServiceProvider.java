package com.asymt.addon.integral.service.provider;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import io.jboot.aop.annotation.Bean;
import com.asymt.addon.integral.service.IntegralAvailablesService;
import com.asymt.addon.integral.model.IntegralAvailables;
import io.jpress.commons.service.JPressServiceBase;

@Bean
public class IntegralAvailablesServiceProvider extends JPressServiceBase<IntegralAvailables> implements IntegralAvailablesService {

    @Override
    public void countIntegral(Integer userId){
        String sql=String.format("select sum(integral) from %s where user_id = ?",DAO._getTableName());
        Integer sumIntegral=Db.queryInt(sql,userId);
        String integralTableName="user_integral";
        String userIntegralPrimaryKey="user_id";
        if(sumIntegral==null){
            Db.deleteById(integralTableName, userIntegralPrimaryKey,userId);
        }else {
            Record userIntegral=Db.findByIds(integralTableName,userIntegralPrimaryKey,userId);
            if(userIntegral==null){
                Db.save(integralTableName,new Record().set(userIntegralPrimaryKey,userId).set("integral",sumIntegral));
            }else {
                Db.update(integralTableName,userIntegralPrimaryKey,userIntegral);
            }
        }
    }
}
