package com.joy.baselib.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2016/10/13.
 */
public class DialogUtils {

    public static void showDialog(Context context, int title, int content, int confirm,
                                  int logout,
                                  DialogInterface.OnClickListener confirmListener,
                                  DialogInterface.OnClickListener logoutListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(confirm, confirmListener)
                .setNegativeButton(logout, logoutListener);
                builder.create().show();
    }
}
