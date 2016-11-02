package com.joy.baselib.util;

/**
 * 文本工具类
 * Created by Administrator on 2016/10/12.
 */
public class TextUtils {

    /**
     * 格式化,保留二位小数
     * @param value
     * @return
     */
    public static String format(double value){
        return String.format("%.2f", value);
    }

}
