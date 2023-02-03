package com.asymt.addon.integral.service.provider;

import com.asymt.addon.integral.model.IntegralAvailables;
import com.asymt.addon.integral.service.IntegralAvailablesService;
import com.jfinal.aop.Inject;
import io.jboot.aop.annotation.Bean;
import com.asymt.addon.integral.service.IntegralDetailsService;
import com.asymt.addon.integral.model.IntegralDetails;
import io.jpress.commons.service.JPressServiceBase;

@Bean
public class IntegralDetailsServiceProvider extends JPressServiceBase<IntegralDetails> implements IntegralDetailsService {

    @Inject
    IntegralAvailablesService integralAvailablesService;
    @Override
    public Object add(IntegralDetails integralDetails){
        IntegralAvailables integralAvailables = toIntegralAvailables(integralDetails);
        integralAvailablesService.save(integralAvailables);
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
}
