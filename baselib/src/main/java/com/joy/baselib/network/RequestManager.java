package com.joy.baselib.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.joy.baselib.util.T;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 网络请求管理类
 * Created by Administrator on 2016/10/13.
 */
public class RequestManager {

    private static RequestManager requestManager = null;

    private VolleyManager request;

    private RequestManager() {
        request = new VolleyManager();
    }

    public static RequestManager getInstance() {
        if(requestManager == null) {
            synchronized (VolleyManager.class) {
                if(requestManager == null) {
                    requestManager = new RequestManager();
                }
            }
        }
        return requestManager;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        request.init(context);
    }

    /**
     * get请求
     * @param url
     * @param responseListener
     * @param errorListener
     */
    public void get(String url, ResponseListener responseListener , ErrorListener errorListener,
                    Map<String, String> map) {
        if(map != null && map.size()!=0) {
            StringBuilder sb = new StringBuilder();
            sb.append(url).append("?");
            if (map != null && map.size() != 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    // 如果请求参数中有中文，需要进行URLEncoder编码,gbk/utf8
                    try {
                        sb.append(entry.getKey()).append("=").append(URLEncoder.
                                encode(entry.getValue(), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    sb.append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }

            url = sb.toString();
        }

        request.get(url, responseListener, errorListener);
    }

    /**
     * post请求
     * @param url
     * @param responseListener
     * @param errorListener
     * @param map
     */
    public void post(String url, ResponseListener responseListener,
                ErrorListener errorListener, Map<String, String> map) {
        request.post(url, responseListener, errorListener, map);
    }

    /**
     * 响应监听器
     */
    public interface ResponseListener extends Response.Listener<String>{

    }

    /**
     * 失败监听器
     */
    public interface ErrorListener extends Response.ErrorListener {

    }

}
