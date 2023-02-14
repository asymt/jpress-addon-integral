package com.asymt.addon.resourcesite.interceptor;

import io.jboot.aop.InterceptorBuilder;
import io.jboot.aop.Interceptors;
import io.jboot.aop.annotation.AutoLoad;
import io.jboot.utils.ClassUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;

/**
 * @author 王喻
 * @date 2023/2/14
 */
@AutoLoad
public class ResourceSiteInterceptorBuilder implements InterceptorBuilder {
    @Override
    public void build(Class<?> targetClass, Method method, Interceptors interceptors) {
        if(UserManagerInterceptor.USER_INFO_CONTROLLER_CLASSNAME.equals(targetClass.getName())){
            interceptors.add(UserManagerInterceptor.class);
        }else if(ArticleOperationInterceptor.ADMIN_ARTICLE_CONTROLLER_CLASSNAME.equals(targetClass.getName())&&
                ArrayUtils.contains(ArticleOperationInterceptor.ARTICLE_OPERATION_METHODNAME,method.getName())){
            interceptors.add(ClassUtil.singleton(ArticleOperationInterceptor.class,false,true));
        }
    }
}
