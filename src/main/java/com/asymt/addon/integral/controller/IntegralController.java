package com.asymt.addon.integral.controller;

import com.asymt.addon.integral.commons.IntegralConsts;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jpress.web.base.AdminControllerBase;

@RequestMapping(value = "/user/integral",viewPath = IntegralConsts.DEFAULT_ADMIN_VIEW)
public class IntegralController  extends AdminControllerBase {

    public void edit() {
        render("user/integral_edit.html");
    }
}
