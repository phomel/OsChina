package com.joy.oschina.util;

import android.app.Activity;

import com.joy.baselib.util.SPUtils;
import com.joy.baselib.util.SnackbarUtils;
import com.joy.oschina.R;
import com.joy.oschina.application.OsChinaApplication;
import com.nostra13.universalimageloader.utils.L;

/**
 * 主题工具类
 * Created by Administrator on 2016/10/19.
 */
public class ThemeUtils {

    private static int style = R.style.BlueTheme;

    public static void changeTheme(Activity activity) {
        int position = (int)SPUtils.get(activity, AppConstant.THEME_COLOR, 0);
        switch (position) {
            case 0:
                style = R.style.BlueTheme;
                SnackbarUtils.color = R.color.themeBlue;
                break;
            case 1:
                style = R.style.BrownTheme;
                SnackbarUtils.color = R.color.themeDarkGray;
                break;
            case 2:
                style = R.style.RedTheme;
                SnackbarUtils.color = R.color.themeDarkOrange;
                break;
            case 3:
                style = R.style.BlueGreyTheme;
                SnackbarUtils.color = R.color.themeDarkBlue;
                break;
            case 4:
                style = R.style.YellowTheme;
                SnackbarUtils.color = R.color.themeOrange;
                break;
            case 5:
                style = R.style.DeepPurpleTheme;
                SnackbarUtils.color = R.color.themePurple;
                break;
            case 6:
                style = R.style.PinkTheme;
                SnackbarUtils.color = R.color.themeRed;
                break;
            case 7:
                style = R.style.GreenTheme;
                SnackbarUtils.color = R.color.themeGreen;
                break;
        }
        activity.setTheme(style);
    }
}
