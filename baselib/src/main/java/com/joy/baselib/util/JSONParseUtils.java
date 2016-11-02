package com.joy.baselib.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import static android.os.Build.VERSION_CODES.M;

/**
 * JSON解析工具类
 * Created by sks on 2016/4/9.
 */
public class JSONParseUtils {

    /**
     * 解析对象
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if(TextUtils.isEmpty(json)){
            return null;
        }
        return JSONObject.parseObject(json, clazz);
    }

    /**
     * 解析数组
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        if(TextUtils.isEmpty(json)){
            return null;
        }
        return new ArrayList<T>(JSONArray.parseArray(json, clazz));
    }

}
