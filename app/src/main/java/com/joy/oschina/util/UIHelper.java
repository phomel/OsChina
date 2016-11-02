package com.joy.oschina.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.joy.oschina.R;
import com.joy.oschina.application.OsChinaApplication;
import com.joy.oschina.bean.Member;
import com.joy.oschina.ui.activity.LoginActivity;
import com.joy.oschina.ui.activity.MainPageActivity;
import com.joy.oschina.ui.activity.PersonalCenterActivity;
import com.joy.oschina.ui.activity.SettingActivity;
import com.joy.oschina.ui.activity.WebActivity;

public class UIHelper {

    /**
     * 跳转到主页面
     * @param context
     */
    public static void forwardMain(Context context) {
        Intent intent = new Intent(context, MainPageActivity.class);
        context.startActivity(intent);
    }

    public static void forwardMain(Activity activity, Member member) {
        Intent intent = new Intent(activity, MainPageActivity.class);
        intent.putExtra("member", member);
        activity.startActivityForResult(intent, 1);
    }

    /**
     * 跳转到设置网络页面(手机内置)
     * @param activity
     */
    public static void forwardNetWorkSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivityForResult(intent, AppConstant.NETWORK_QUEST_CODE);
    }

    public static void forwardLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void forwardLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, 0);
    }

    public static void forwardSetting(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    //跳转到Web页面
    public static void forwardProblemWeb(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void forwardPersonalCenter(Activity activity) {
        Intent intent = new Intent(activity, PersonalCenterActivity.class);
        intent.putExtra(AppConstant.SAVE_INSTANCE, OsChinaApplication.member);
        activity.startActivityForResult(intent, 1);
    }
}
