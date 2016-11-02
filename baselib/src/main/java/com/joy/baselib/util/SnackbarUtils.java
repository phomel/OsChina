package com.joy.baselib.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.joy.baselib.R;

/**
 * Created by Administrator on 2016/10/13.
 */
public class SnackbarUtils {

    public static int color;

    public static void makeText(Context context, View view, String str, int duration) {
        Snackbar snackbar = Snackbar.make(view, str, duration);
        snackbar.getView().setBackgroundColor(context.getResources().getColor(color));
        snackbar.show();
    }
}
