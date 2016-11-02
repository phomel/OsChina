package com.joy.baselib.network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Volley请求管理类
 * Created by Administrator on 2016/10/13.
 */
public class VolleyManager {

    private static RequestQueue requestQueue = null;

    /**
     * 初始化请求队列
     * @param context
     */
    public void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * 添加Request到队列
     * @param request
     * @param <T>
     */
    private <T> void addRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }

    /**
     * 添加Request到队列（带标签）
     * @param request
     * @param tag
     * @param <T>
     */
    private <T> void addRequestQueue(Request<T> request, String tag) {
        if(!TextUtils.isEmpty(tag)) {
            request.setTag(tag);
        }
        addRequestQueue(request);
    }

    /**
     * get请求
     * @param url 请求url
     * @param listener 响应监听器
     * @param errorListener 请求错误监听器
     * @return
     */
    public Request get(String url, Response.Listener<String> listener,
                       Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(url, listener, errorListener);
        addRequestQueue(stringRequest);
        return stringRequest;
    }

    /**
     * post请求
     * @param url
     * @param listener
     * @param errorListener
     * @param map
     * @return
     */
    public Request post(String url, Response.Listener<String> listener,
                        Response.ErrorListener errorListener, Map<String, String> map) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                listener, errorListener, map);
        addRequestQueue(stringRequest);
        return stringRequest;
    }

    /**
     * 根据标签取消请求
     * @param tag
     */
    public void cancelRequest(String tag) {
        requestQueue.cancelAll(tag);
    }

}
