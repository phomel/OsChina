package com.joy.baselib.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast信息类
 * Created by Administrator on 2016/10/12.
 */

public class T {

    /**
     * 显示Toast信息（短时间）
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast信息（短时间）
     * @param context
     * @param message
     */
    public static void showShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast信息（长时间）
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示Toast信息（长时间）
     * @param context
     * @param message
     */
    public static void showLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 在子线程中显示Toast消息
     * @param activity
     * @param message
     */
    public static void showOnUI(final Activity activity, final int message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShort(activity, message);
            }
        });
    }

    /**
     * 在子线程中显示Toast消息
     * @param activity
     * @param message
     */
    public static void showOnUI(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShort(activity, message);
            }
        });
    }

}
