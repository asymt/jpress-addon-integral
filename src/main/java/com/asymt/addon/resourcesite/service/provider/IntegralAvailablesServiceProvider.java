package com.asymt.addon.resourcesite.service.provider;

import com.asymt.addon.resourcesite.model.IntegralAvailables;
import com.asymt.addon.resourcesite.service.IntegralAvailablesService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import io.jboot.aop.annotation.Bean;
import io.jboot.db.model.Columns;
import io.jpress.commons.service.JPressServiceBase;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
            Record userIntegral=Db.findById(integralTableName,userIntegralPrimaryKey,userId);
            if(userIntegral==null){
                Db.save(integralTableName,new Record().set(userIntegralPrimaryKey,userId).set("integral",sumIntegral));
            }else {
                Db.update(integralTableName,userIntegralPrimaryKey,userIntegral);
            }
        }
    }
}
