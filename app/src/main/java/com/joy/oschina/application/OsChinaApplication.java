package com.joy.oschina.application;

import android.content.Context;
import android.view.MenuItem;

import com.joy.baselib.application.CoreApplication;
import com.joy.baselib.util.SPUtils;
import com.joy.baselib.util.file.SerializableUtils;
import com.joy.oschina.bean.Member;
import com.joy.oschina.util.AppConstant;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/13.
 */
public class OsChinaApplication extends CoreApplication {

    public static Context context;
    public static boolean isLogin;
    public static MenuItem menuItem;
    public static Member member;

    @Override
    public void onCreate() {
        context = this;
        super.onCreate();
    }
}
