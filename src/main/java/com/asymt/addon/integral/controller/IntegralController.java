package com.asymt.addon.integral.controller;

import com.asymt.addon.integral.commons.IntegralConsts;
import com.asymt.addon.integral.model.IntegralDetails;
import com.asymt.addon.integral.service.IntegralDetailsService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.log.Log;
import io.jboot.aop.annotation.Transactional;
import io.jboot.utils.DateUtil;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jpress.web.base.AdminControllerBase;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

@RequestMapping(value = "/user/integral",viewPath = IntegralConsts.DEFAULT_ADMIN_VIEW)
public class IntegralController  extends AdminControllerBase {
    private static final Log LOG=Log.getLog(IntegralController.class);
    @Inject
    IntegralDetailsService integralDetailsService;
    public void edit() {
        Long uid = getParaToLong();
        setAttr("userId", uid);
        setAttr("currentDate", new Date());
        render("user/integral_edit.html");
    }

    @Transactional(rollbackForRetFail = true)
    public Ret doAdd(){
        IntegralDetails integralDetails=getBean(IntegralDetails.class);
        if(integralDetails.getIntegral()==null || integralDetails.getIntegral()<1){
            return Ret.fail().set("message", "积分不能为空且不能小于1").set("errorCode", 3);
        }
        if(integralDetails.getExpire()==null || !DateUtil.isAfter(integralDetails.getExpire(),new Date())){
            return Ret.fail().set("message", "过期时间不能为空且必须是以后的时间").set("errorCode", 4);
        }
        integralDetails.setCreated(new Date());
        try {
            integralDetailsService.add(integralDetails);
        }catch (Exception e){
            LOG.error("添加积分失败！",e);
            return Ret.fail().set("message","添加积分失败！").set("errorCode",5);
        }
        return Ret.ok();
    }
}
