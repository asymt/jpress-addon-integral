package com.asymt.addon.integral;

import io.jpress.core.addon.AddonBase;
import io.jpress.core.addon.AddonInfo;
import io.jpress.core.addon.AddonUtil;
import io.jpress.core.menu.MenuGroup;
import io.jpress.core.menu.MenuManager;

import java.sql.SQLException;

/**
 *用户积分插件
 */
public class IntegralAddon extends AddonBase  {


    @Override
    public void onInstall(AddonInfo addonInfo) {
        try {
            AddonUtil.execSqlFile(addonInfo, "sql/install.sql");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUninstall(AddonInfo addonInfo) {
        try {
            AddonUtil.execSqlFile(addonInfo, "sql/uninstall.sql");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onStart(AddonInfo addonInfo) {
        /**
         * 删除系统菜单，用于添加增强后的同类型菜单
         */
        MenuManager.me().deleteMenuItem("用户管理--/admin/user/list");
        /**
         *  添加菜单到后台
         */


    }

    @Override
    public void onStop(AddonInfo addonInfo) {

        /**
         *  删除添加的菜单
         */
        MenuManager.me().deleteMenuGroup("message");

    }
}
