package com.joy.baselib.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * App工具类
 * Created by sks on 2016/4/9.
 */
public class AppUtils {

    /**
     * 获取应用程序包信息(包含版本名称和版本号);
     * @param context
     * @return 当前应用的版本名称
     */
    public static PackageInfo getPackageInfo(Context context) {
        try {
            //packageInfo.versionName、packageInfo.AttrUtils
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查某个应用是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkAPP(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
