package com.asymt.addon.integral;

import com.jfinal.log.Log;
import io.jpress.core.addon.AddonBase;
import io.jpress.core.addon.AddonInfo;
import io.jpress.core.addon.AddonUtil;

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
