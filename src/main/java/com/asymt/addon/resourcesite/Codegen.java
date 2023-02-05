package com.asymt.addon.resourcesite;

import io.jpress.codegen.AddonGenerator;

public class Codegen {

    private static String dbUrl = "jdbc:mysql://192.168.50.250:3306/3dresources";
    private static String dbUser = "3dresources";
    private static String dbPassword = "123456";

    private static String addonName = "integral";
    private static String dbTables = "integral_availables,integral_details,user_integral";
    private static String modelPackage = "com.asymt.addon.integral.model";
    private static String servicePackage = "com.asymt.addon.integral.service";


    public static void main(String[] args) {

        AddonGenerator moduleGenerator = new AddonGenerator(addonName, dbUrl, dbUser, dbPassword, dbTables, modelPackage, servicePackage);
//        moduleGenerator.setGenUI(true);
        moduleGenerator.gen();

    }

}
