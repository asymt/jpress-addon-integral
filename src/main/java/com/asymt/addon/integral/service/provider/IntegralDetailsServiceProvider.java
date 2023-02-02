package com.asymt.addon.integral.service.provider;

import com.jfinal.plugin.activerecord.Page;
import io.jboot.aop.annotation.Bean;
import com.asymt.addon.integral.service.IntegralDetailsService;
import com.asymt.addon.integral.model.IntegralDetails;
import io.jboot.db.model.Column;
import io.jpress.commons.service.JPressServiceBase;

@Bean
public class IntegralDetailsServiceProvider extends JPressServiceBase<IntegralDetails> implements IntegralDetailsService {


}
