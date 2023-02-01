package com.asymt.addon.integral.interceptor;

import com.asymt.addon.integral.commons.IntegralConsts;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.log.Log;
import com.jfinal.log.Log4jLogFactory;
import io.jpress.core.addon.annotation.GlobalInterceptor;

@GlobalInterceptor
public class UserManagerInterceptor implements Interceptor {
    private final static Log LOG = Log.getLog(UserManagerInterceptor.class);
    @Override
    public void intercept(Invocation invocation) {
        invocation.invoke();
        if(invocation.getActionKey().startsWith("/admin/user/detail")){
            LOG.info("渲染带积分详情的用户信息页");
            String view=invocation.getController().getRender().getView();
            if (view != null && view.length() > 0 && view.charAt(0) != '/') {
                view = "/addons/com.asymt.addon.integral"+ IntegralConsts.DEFAULT_ADMIN_VIEW + view;
            }
            invocation.getController().getRender().setView(view);
        }
    }
}
