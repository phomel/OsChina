package com.joy.baselib.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * 图片加载类
 * Created by Administrator on 2016/10/12.
 */

public class ImageLoader {

    private static ImageLoader imageLoader = null;
    private UniversalImageLoader loader;

    private ImageLoader(Context context){
        loader = UniversalImageLoader.getInstance();
    }

    public static ImageLoader getInstance(Context context) {
        if(imageLoader == null) {
            synchronized(ImageLoader.class) {
                if(imageLoader == null) {
                    imageLoader = new ImageLoader(context);
                }
            }
        }
        return imageLoader;
    }

    /**
     * 加载图片
     * @param url 图片url
     * @param imageView
     */
    public void loadImage(String url, ImageView imageView) {
        loader.loadImage(url, imageView);
    }

    /**
     * 加载图片
     * @param url 图片url
     * @param imageView
     * @param defaultImage 默认加载图片
     * @param failImage 加载失败图片
     */
    public void loadImage(String url, ImageView imageView, int defaultImage, int failImage){
        loader.loadImage(url, imageView, defaultImage, failImage);
    }

}
