package com.joy.baselib.network;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.joy.baselib.util.L;
import com.joy.baselib.util.NetworkUtils;

/**
 * 网络状态监听Service
 * Created by Administrator on 2016/10/12.
 */
public class NetWorkStateService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkUtils.initNetStatus(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);
        L.e("-----网络状态监听开始------");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        L.e("-----网络状态监听结束------");
        super.onDestroy();
    }

    BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
                L.e("网络状态改变");
                //初始化网络状态
                NetworkUtils.initNetStatus(context);
            }
        }
    };

}
