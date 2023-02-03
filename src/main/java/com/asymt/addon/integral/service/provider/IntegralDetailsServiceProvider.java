package com.asymt.addon.integral.service.provider;

import io.jboot.aop.annotation.Bean;
import com.asymt.addon.integral.service.IntegralDetailsService;
import com.asymt.addon.integral.model.IntegralDetails;
import io.jpress.commons.service.JPressServiceBase;

@Bean
public class IntegralDetailsServiceProvider extends JPressServiceBase<IntegralDetails> implements IntegralDetailsService {

    @Override
    public Object add(IntegralDetails integralDetails){
        return save(integralDetails);
    }
}
