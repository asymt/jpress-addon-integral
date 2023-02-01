package com.asymt.addon.integral.controller;

import com.asymt.addon.integral.commons.IntegralConsts;
import com.asymt.addon.integral.model.IntegralDetails;
import com.asymt.addon.integral.service.IntegralDetailsService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jboot.web.validate.EmptyValidate;
import io.jboot.web.validate.Form;
import io.jpress.model.User;
import io.jpress.service.UserService;
import io.jpress.web.base.AdminControllerBase;

import java.util.Set;

@RequestMapping(value = "/admin/user/detail", viewPath = IntegralConsts.DEFAULT_ADMIN_VIEW)
public class _IntegralDetailsController extends AdminControllerBase {

    @Inject
    private IntegralDetailsService integralDetailsService;
    @Inject
    private UserService userService;

    public void integral(){
        Long uid = getParaToLong();
        User user = userService.findById(uid);
        setAttr("user", user);
        Page<IntegralDetails> entries= integralDetailsService._paginateByUserId(getPagePara(), getPageSizePara(),user.getId());
        setAttr("page", entries);
        render("user/detail_integral.html");
    }

}
