package com.joy.oschina.util;

import android.graphics.Typeface;
import android.widget.TextView;

import com.joy.oschina.application.OsChinaApplication;

/**
 * 字体工具类
 */
public class TypefaceUtils {

    private static Typeface typeface;

    private static Typeface getTypeface() {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(OsChinaApplication.instance.getAssets(), "icons.ttf");
        }
        return typeface;
    }

    public static void setIconText(TextView view, String text) {
        view.setText(text);
        view.setTypeface(TypefaceUtils.getTypeface());
    }

}
