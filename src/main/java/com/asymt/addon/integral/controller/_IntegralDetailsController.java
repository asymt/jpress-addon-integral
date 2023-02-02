package com.asymt.addon.integral.controller;

import com.asymt.addon.integral.commons.IntegralConsts;
import com.asymt.addon.integral.model.IntegralDetails;
import com.asymt.addon.integral.service.IntegralDetailsService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import io.jboot.db.model.Columns;
import io.jboot.utils.DateUtil;
import io.jboot.utils.StrUtil;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jboot.web.validate.EmptyValidate;
import io.jboot.web.validate.Form;
import io.jpress.model.User;
import io.jpress.service.UserService;
import io.jpress.web.base.AdminControllerBase;

import java.util.Date;
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
        setAttr("page", entries);
        render("user/detail_integral.html");
    }

}
