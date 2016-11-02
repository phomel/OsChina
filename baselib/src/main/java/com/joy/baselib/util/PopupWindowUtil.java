package com.joy.baselib.util;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2016/9/12.
 */
public class PopupWindowUtil {

    public static PopupWindow setPopupWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        return popupWindow;
    }

    public static PopupWindow setPopupWindow(View view, int style) {
        PopupWindow popupWindow = setPopupWindow(view);
        popupWindow.setAnimationStyle(style);
        return popupWindow;
    }

    public void setBackgroundAlpha(Activity activity, float backgroundAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = backgroundAlpha;
        activity.getWindow().setAttributes(lp);
    }
}
