package com.asymt.addon.integral.service.provider;

import io.jboot.aop.annotation.Bean;
import com.asymt.addon.integral.service.IntegralAvailablesService;
import com.asymt.addon.integral.model.IntegralAvailables;
import io.jpress.commons.service.JPressServiceBase;

@Bean
public class IntegralAvailablesServiceProvider extends JPressServiceBase<IntegralAvailables> implements IntegralAvailablesService {

}