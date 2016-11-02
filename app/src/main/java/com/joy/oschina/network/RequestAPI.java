package com.joy.oschina.network;

import com.joy.oschina.util.AppConstant;

/**
 * 请求API
 */
public class RequestAPI {

    //基础URL
    public static final String BASE_URL = "http://git.oschina.net/api/v3/";
    public static final String FEATURED = "projects/featured";  //推荐项目
    public static final String POPULAR = "projects/popular";    //热门项目
    public static final String LATEST = "projects/latest";      //最近更新
    public static final String DYNAMIC = "events/user/";      //动态
    public static final String LANGUAGE = "projects/languages";  //语言
    public static final String SHAKE = "projects/random?luck=1"; //摇一摇
    public static final String LOGIN = "session";    //登录

    //mine相关URL
    public static String getMineUrl(String type, int userId) {
        String url = "";
        switch (type) {
            case AppConstant.DYNAMIC:
                url = RequestAPI.getUrl(RequestAPI.DYNAMIC + userId);
                break;
            case AppConstant.TYPE_PROJECT:
                url = RequestAPI.getUrl("user/" + userId + "/projects");
                break;
            case AppConstant.TYPE_STAR:
                url = RequestAPI.getUrl("user/" + userId + "/stared_projects");
                break;
            case AppConstant.TYPE_WATCH:
                url = RequestAPI.getUrl("user/" + userId + "/watched_projects");
                break;
        }
        return url;
    }

    /**
     * 拼接完整URL
     *
     * @param relativeUrl
     * @return
     */
    public static String getUrl(String relativeUrl) {
        return RequestAPI.BASE_URL + relativeUrl;
    }

    public static String getReadmeURL(int projectId) {
        return "projects/" + projectId + "/readme";
    }

}
