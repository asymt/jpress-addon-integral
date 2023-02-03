package com.asymt.addon.integral.controller;

import com.asymt.addon.integral.commons.IntegralConsts;
import com.asymt.addon.integral.model.IntegralDetails;
import com.asymt.addon.integral.service.IntegralDetailsService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import io.jboot.utils.DateUtil;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jpress.web.base.AdminControllerBase;

import java.util.Date;

@RequestMapping(value = "/user/integral",viewPath = IntegralConsts.DEFAULT_ADMIN_VIEW)
public class IntegralController  extends AdminControllerBase {
    @Inject
    IntegralDetailsService integralDetailsService;
    public void edit() {
        Long uid = getParaToLong();
        setAttr("userId", uid);
        render("user/integral_edit.html");
    }

    public void doAdd(){
        IntegralDetails integralDetails=getBean(IntegralDetails.class);
        if(integralDetails.getIntegral()==null || integralDetails.getIntegral()<1){
            renderJson(Ret.fail().set("message", "积分不能为空且不能小于1").set("errorCode", 3));
            return;
        }
        if(integralDetails.getExpire()==null || !DateUtil.isAfter(integralDetails.getExpire(),new Date())){
            renderJson(Ret.fail().set("message", "过期时间不能为空且必须是以后的时间").set("errorCode", 4));
            return;
        }
        integralDetails.setCreated(new Date());
        integralDetailsService.add(integralDetails);
        renderOkJson();
    }
}
