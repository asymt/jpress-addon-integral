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
        render("user/integral_details_list.html");
    }

    public void edit() {
        int entryId = getParaToInt(0, 0);
//        JpressAddonMessage entry = entryId > 0 ? service.findById(entryId) : null;
//        setAttr("jpressAddonMessage", entry);
//        render("jpress_addon_message_edit.html");
    }


    public void doSave() {
//        JpressAddonMessage entry = getModel(JpressAddonMessage.class,"jpressAddonMessage");
//        service.saveOrUpdate(entry);
//        renderJson(Ret.ok().set("id", entry.getId()));
    }



    public void doDel() {
        Long id = getIdPara();
        render(integralDetailsService.deleteById(id) ? Ret.ok() : Ret.fail());
    }


    @EmptyValidate(@Form(name = "ids"))
    public void doDelByIds() {
        Set<String> idsSet = getParaSet("ids");
        if (integralDetailsService.batchDeleteByIds(idsSet.toArray())){
            for (String id : idsSet){
                integralDetailsService.deleteById(Long.valueOf(id));
            }
        }
        renderOkJson();
    }
}
