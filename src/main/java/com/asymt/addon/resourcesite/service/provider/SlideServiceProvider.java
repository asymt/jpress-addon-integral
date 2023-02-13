package com.asymt.addon.resourcesite.service.provider;

import io.jboot.aop.annotation.Bean;
import com.asymt.addon.resourcesite.service.SlideService;
import com.asymt.addon.resourcesite.model.Slide;
import io.jpress.commons.service.JPressServiceBase;

@Bean
public class SlideServiceProvider extends JPressServiceBase<Slide> implements SlideService {

}