package com.asymt.addon.resourcesite;

import com.asymt.addon.resourcesite.interceptor.UserManagerInterceptor;
import com.asymt.addon.resourcesite.task.ExpireIntegralTask;
import com.jfinal.log.Log;
import com.jfinal.template.source.FileSourceFactory;
import com.jfinal.template.source.ISource;
import io.jboot.components.schedule.JbootScheduleManager;
import io.jboot.db.datasource.DataSourceConfigManager;
import io.jpress.core.addon.AddonBase;
import io.jpress.core.addon.AddonInfo;
import io.jpress.core.addon.AddonUtil;
import javassist.ClassClassPath;
import javassist.ClassPool;

import java.sql.SQLException;

/**
 *用户积分插件
 */
public class IntegralAddon extends AddonBase  {
    private final static Log LOG = Log.getLog(IntegralAddon.class);

    @Override
    public void onInstall(AddonInfo addonInfo) {
        try {
            AddonUtil.execSqlFile(addonInfo, "sql/install.sql");
            LOG.info("插件《{}》安装完成！",addonInfo.getTitle());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUninstall(AddonInfo addonInfo) {
        try {
            AddonUtil.execSqlFile(addonInfo, "sql/uninstall.sql");
            LOG.info("插件《{}》卸载完成！",addonInfo.getTitle());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onStart(AddonInfo addonInfo) {
        AddonDb.setConfigName(addonInfo.getId());
        /**
         * 添加sql模板,多次停止启用也只添加一次
         */
        ISource sqlTemplateSource=new FileSourceFactory().getSource(AddonUtil.getAddonBasePath(addonInfo.getId()),"/sqlTemplate/all.sql",addonInfo.getArp().getEngine().getEncoding());
        if(addonInfo.getArp()!=null&&addonInfo.getArp().getSqlKit().getSqlMapEntrySet().isEmpty()) {
            addonInfo.getArp().stop();
            addonInfo.getArp().addSqlTemplate(sqlTemplateSource);
            addonInfo.getArp().start();
        }
        /**
         * 添加自动过期积分的任务
         */
        JbootScheduleManager.me().addSchedule(ExpireIntegralTask.class);

        /**
         *  添加菜单到后台
         */

        LOG.info("插件《{}》启动完成！",addonInfo.getTitle());

    }

    @Override
    public void onStop(AddonInfo addonInfo) {

        /**
         *  删除添加的菜单
         */
        LOG.info("插件《{}》停止完成！",addonInfo.getTitle());

    }
}
