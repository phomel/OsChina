package com.joy.baselib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.joy.baselib.network.NetWorkStateService;

import java.util.ArrayList;
import java.util.List;

/**
 * 核心工具类
 * Created by sks on 2016/4/9.
 */
public class CoreUtils {

    //Activity列表
    public static ArrayList<Activity> activityList = new ArrayList<Activity>();

    /**
     * 添加Activity到列表中
     * @param activity
     */
    public static void addAppActivity(Activity activity){
        if(!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    /**
     * 从列表移除Activity
     * @param activity
     */
    public static void removeAppActivity(Activity activity){
        if(activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    /**
     * 退出应用程序
     */
    public static void exitApp(Context context){
        context.stopService(new Intent(context, NetWorkStateService.class));
        L.e("haha",  "------------销毁Activity size:" + activityList.size());
            for (Activity ac : activityList) {
                if(!ac.isFinishing()){
                    ac.finish();
                }
        }
        activityList.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 清空List集合
     * @param list
     */
    public static void clearList(List<?> list){
        if(list!=null){
            list.clear();
        }
    }

}
