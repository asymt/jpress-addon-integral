package com.asymt.addon.resourcesite;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;

/**
 * @author 王喻
 * @date 2023/2/9
 */
public class AddonDb{
    private static String configName;

    public static void setConfigName(String configName){
        AddonDb.configName=configName;
    }

    public static DbPro getDbPro(){
        return Db.use(configName);
    }

}
