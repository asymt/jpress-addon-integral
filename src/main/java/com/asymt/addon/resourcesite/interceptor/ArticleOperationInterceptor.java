package com.asymt.addon.resourcesite.interceptor;

import com.asymt.addon.resourcesite.model.MaterialExtend;
import com.asymt.addon.resourcesite.service.MaterialExtendService;
import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.log.Log;
import io.jpress.web.base.ControllerBase;

import java.util.Set;

/**
 * @author 王喻
 * @date 2023/2/14
 */
public class ArticleOperationInterceptor implements Interceptor {
    private final static Log LOG = Log.getLog(ArticleOperationInterceptor.class);
    public final static String ADMIN_ARTICLE_CONTROLLER_CLASSNAME="io.jpress.module.article.controller.admin._ArticleController";

    public final static String[] ARTICLE_OPERATION_METHODNAME={"doWriteSave","doDel","doDelByIds"};

    @Inject
    private MaterialExtendService materialExtendService;
    @Override
    public void intercept(Invocation inv) {
        if(ARTICLE_OPERATION_METHODNAME[0].equals(inv.getMethodName())){
            doWriteSave(inv);
        }else if(ARTICLE_OPERATION_METHODNAME[1].equals(inv.getMethodName())){
            doDel(inv);
        }else {
            doDelByIds(inv);
        }
    }

    private void doWriteSave(Invocation inv){
        inv.invoke();
        ControllerBase controllerBase= (ControllerBase) inv.getController();
        Long id=controllerBase.getAttr("articleId");
        MaterialExtend materialExtend=controllerBase.getBean(MaterialExtend.class,"materialExtend");
        materialExtend.setArticleId(id);
        materialExtendService.saveOrUpdate(materialExtend);
    }

    private void doDel(Invocation inv){
        ControllerBase controllerBase= (ControllerBase) inv.getController();
        Long id = controllerBase.getIdPara();
        materialExtendService.deleteById(id);
        inv.invoke();
    }

    private void doDelByIds(Invocation inv){
        ControllerBase controllerBase= (ControllerBase) inv.getController();
        Set<String> idsSet = controllerBase.getParaSet("ids");
        materialExtendService.batchDeleteByIds(idsSet);
        inv.invoke();
    }
}
