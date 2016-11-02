package com.joy.baselib.util.file;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * SD卡管理类
 * Created by Administrator on 2016/10/12.
 */
public class SDCardManager {

    /**
     * 判断SD卡是否可用
     * @return
     */
    public static boolean isExistSD(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡根路径
     * @return
     */
    public static String getSDCardPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡缓存目录
     * @param context
     * @return
     */
    public static String getSDCacheDir(Context context){
        if(isExistSD()){
            return context.getExternalCacheDir().getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取手机内部空间大小
     * @return
     */
    public static long getTotalInternalSize() {
        //Gets the Android data directory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        //每个block 占字节数
        long blockSize = stat.getBlockSize();
        //block总数
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取手机内部可用空间大小
     * @return
     */
    public static long getAvailableInternalSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取SD卡大小
     * @return
     */
    public static long getSDCardSize() {
        if (isExistSD()) {
            StatFs stat = new StatFs(getSDCardPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        }
        return 0;
    }

    /**
     * 获取SD卡可用空间大小,单位byte
     * @return
     */
    public static long getAvailableSDCardSize() {
        if (isExistSD()) {
            StatFs stat = new StatFs(getSDCardPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        }
        return 0;
    }

}
