package com.joy.baselib.image;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.joy.baselib.application.CoreApplication;
import com.joy.baselib.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

/**
 * UniversalImageLoader图片加载
 * Created by Administrator on 2016/10/12.
 */
public class UniversalImageLoader {

    private static UniversalImageLoader universalImageLoader;

    private UniversalImageLoader() {

    }

    public static UniversalImageLoader getInstance() {
        if(universalImageLoader == null) {
            synchronized(UniversalImageLoader.class) {
                if(universalImageLoader == null) {
                    universalImageLoader = new UniversalImageLoader();
                }
            }
        }
        return universalImageLoader;
    }

    /**
     * 初始化ImageLoader
     */
    public void initImageLoader(Context context){
        // 获取我们应用的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory/8;

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.img_pictures_no) //下载时显示的图片
                .showImageOnFail(R.mipmap.img_def_error) //下载失败时显示的图片
                .displayer(new FadeInBitmapDisplayer(500)) //图片加载成功后渐入动画
                .cacheInMemory(true) //设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) //设置下载的图片是否缓存在SD卡
                .build();

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5) //指定线程池大小
                .tasksProcessingOrder(QueueProcessingType.LIFO) //指定图片加载策略，后进先出
                .memoryCache(new LruMemoryCache(cacheMemory / 8)) //内存缓存
                .diskCache(new UnlimitedDiscCache(new File(CoreApplication.IMAGE_DIR))) //设置硬盘缓存
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context, 5000, 30000)) //设置超时时间，第一个参数连接超时，第二个是读取超时
                .defaultDisplayImageOptions(options)
//                .writeDebugLogs() //调试日志
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void loadImage(String url, ImageView imageView){
        ImageLoader.getInstance().displayImage(url, imageView);
    }

    /**
     * 加载图片
     * @param url 图片url
     * @param imageView View
     * @param defaultImage 下载时默认图片
     * @param failImage 加载错误图片
     */
    public void loadImage(String url, ImageView imageView, int defaultImage, int failImage){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage) //下载时显示的图片
                .showImageOnFail(failImage) //下载失败时显示的图片
                .displayer(new FadeInBitmapDisplayer(500)) //图片加载成功后渐入动画
                .cacheInMemory(true) //设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) //设置下载的图片是否缓存在SD卡
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    /**
     * 加载图片
     * @param url
     * @param listener
     */
    private void loadImage(final String url, final SimpleImageLoadingListener listener){
        ImageLoader.getInstance().loadImage(url, listener);
    }

    /**
     * 加载图片,指定图片最大宽和高
     * @param url
     * @param maxWidth
     * @param maxHeight
     * @param listener
     */
    private void loadImage(final String url, int maxWidth, int maxHeight,
            final SimpleImageLoadingListener listener){
        //指定图片大小
        ImageSize imageSize = new ImageSize(maxWidth,maxHeight);
        ImageLoader.getInstance().loadImage(url, imageSize, listener);
    }

}
