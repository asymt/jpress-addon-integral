package com.asymt.addon.resourcesite.service.provider;

import io.jboot.aop.annotation.Bean;
import com.asymt.addon.resourcesite.service.MaterialExtendService;
import com.asymt.addon.resourcesite.model.MaterialExtend;
import io.jpress.commons.service.JPressServiceBase;

@Bean
public class MaterialExtendServiceProvider extends JPressServiceBase<MaterialExtend> implements MaterialExtendService {
    @Override
    public Object saveOrUpdate(MaterialExtend model) {
        return super.saveOrUpdate(model);
    }
}
