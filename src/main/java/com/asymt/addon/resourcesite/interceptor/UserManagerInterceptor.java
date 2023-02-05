package com.asymt.addon.resourcesite.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Action;
import com.jfinal.log.Log;
import io.jpress.core.addon.annotation.GlobalInterceptor;
import io.jpress.core.addon.controller.AddonControllerManager;

@GlobalInterceptor
public class UserManagerInterceptor implements Interceptor {
    private final static Log LOG = Log.getLog(UserManagerInterceptor.class);
    @Override
    public void intercept(Invocation invocation) {
        invocation.invoke();
        if(invocation.getActionKey().startsWith("/admin/user/detail")){
            Action integralAction= AddonControllerManager.getAction("/admin/user/detail/integral",new String[0]);
            LOG.info("积分明细action为：{}",integralAction);
            LOG.info("渲染带积分详情的用户信息页");
            String view=invocation.getController().getRender().getView();
            if (view != null && view.length() > 0 && view.charAt(0) != '/') {
                view = integralAction.getViewPath() + view;
            }
            invocation.getController().getRender().setView(view);
        }
    }
}
