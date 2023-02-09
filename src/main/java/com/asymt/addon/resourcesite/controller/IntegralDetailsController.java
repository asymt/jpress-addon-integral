package com.asymt.addon.resourcesite.controller;

import com.asymt.addon.resourcesite.commons.IntegralConsts;
import com.asymt.addon.resourcesite.model.IntegralDetails;
import com.asymt.addon.resourcesite.service.IntegralDetailsService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import io.jboot.db.model.Columns;
import io.jboot.utils.DateUtil;
import io.jboot.utils.StrUtil;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jpress.model.User;
import io.jpress.service.UserService;
import io.jpress.web.base.AdminControllerBase;

import java.util.Date;

/**
 * @author 喻名堂
 */
@RequestMapping(value = "/admin/user/detail", viewPath = IntegralConsts.DEFAULT_ADMIN_VIEW)
public class IntegralDetailsController extends AdminControllerBase {
    private static final Log LOG = Log.getLog(IntegralDetailsController.class);
    @Inject
    private IntegralDetailsService integralDetailsService;
    @Inject
    private UserService userService;

    public void integral(){
        Long uid = getParaToLong();
        User user = userService.findById(uid);
        setAttr("user", user);
        Long type = getParaToLong("type");

        Date startDate = null;
        Date endDate = null;

        String dateRange = getPara("dateRange");
        if (StrUtil.isNotBlank(dateRange) && dateRange.contains("~")) {
            String[] startAndEndDateStrings = dateRange.split("~");
            startDate = DateUtil.parseDate(startAndEndDateStrings[0].trim());
            endDate = DateUtil.parseDate(startAndEndDateStrings[1].trim());
        }

        Columns columns = Columns.create("type", type);

        if (startDate != null && endDate != null) {
            columns.gt("created", startDate);
            columns.le("created", DateUtil.addDays(endDate, 1));
        }

        columns.eq("user_id", user.getId());
        Page<IntegralDetails> entries= integralDetailsService.paginateByColumns(getPagePara(), getPageSizePara(),columns,"created desc");
        setAttr("integralDetails", entries);
        render("user/detail_integral.html");
    }

    public void integralEdit() {
        Long uid = getParaToLong();
        setAttr("userId", uid);
        setAttr("currentDate", new Date());
        render("user/integral_edit.html");
    }

    public void integralDoAdd(){
        IntegralDetails integralDetails=getBean(IntegralDetails.class);
        if(integralDetails.getIntegral()==null || integralDetails.getIntegral()<1){
            renderJson(Ret.fail().set("message", "积分不能为空且不能小于1").set("errorCode", 3));
            return ;
        }
        if(integralDetails.getExpire()==null || !DateUtil.isAfter(integralDetails.getExpire(),new Date())){
            renderJson(Ret.fail().set("message", "过期时间不能为空且必须是以后的时间").set("errorCode", 4));
            return ;
        }
        integralDetails.setCreated(new Date());
        try {
            integralDetailsService.add(integralDetails);
        }catch (Exception e){
            LOG.error("添加积分失败！",e);
            renderJson(Ret.fail().set("message","添加积分失败！").set("errorCode",5));
            return;
        }
        renderOkJson();
    }

}
