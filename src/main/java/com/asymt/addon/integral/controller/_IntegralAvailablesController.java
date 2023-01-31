package com.asymt.addon.integral.controller;

import com.asymt.addon.integral.model.IntegralAvailables;
import com.asymt.addon.integral.service.IntegralAvailablesService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jboot.web.validate.EmptyValidate;
import io.jboot.web.validate.Form;
import io.jpress.JPressConsts;
import io.jpress.core.menu.annotation.AdminMenu;
import io.jpress.web.base.AdminControllerBase;

import java.util.Set;

@RequestMapping(value = "/admin/integral/jpress_addon_integral", viewPath = "/views/admin")
public class _IntegralAvailablesController extends AdminControllerBase {

    @Inject
    private IntegralAvailablesService service;


    @AdminMenu(text = "可用积分列表", groupId = JPressConsts.SYSTEM_MENU_USER)
    public void list() {
        Page<IntegralAvailables> entries=service.paginate(getPagePara(), 10);
        setAttr("page", entries);
        render("jpress_addon_integral_available_list.html");
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
        render(service.deleteById(id) ? Ret.ok() : Ret.fail());
    }


    @EmptyValidate(@Form(name = "ids"))
    public void doDelByIds() {
        Set<String> idsSet = getParaSet("ids");
        if (service.batchDeleteByIds(idsSet.toArray())){
            for (String id : idsSet){
                service.deleteById(Long.valueOf(id));
            }
        }
        renderOkJson();
    }
}
