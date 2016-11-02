package com.joy.oschina.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.joy.baselib.network.NetWorkStateService;
import com.joy.baselib.util.CoreUtils;
import com.joy.baselib.util.DialogUtils;
import com.joy.baselib.util.NetworkUtils;
import com.joy.baselib.util.SPUtils;
import com.joy.oschina.R;
import com.joy.oschina.application.OsChinaApplication;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/13.
 */
public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.iv_logo)
    public ImageView mImageView;

    private Context context;
    private ObjectAnimator animator;
    private boolean mBoolean = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        context = this;
        ButterKnife.bind(this);
        initAnimation();
    }

    private void initAnimation() {
//        mImageView = (ImageView) findViewById(R.id.iv_logo);
        //启动网络监听服务
        startService(new Intent(this, NetWorkStateService.class));
        //属性动画
        PropertyValuesHolder pv1 = PropertyValuesHolder.ofFloat("alpha", 0.1f, 1.0f);
        PropertyValuesHolder pv2 = PropertyValuesHolder.ofFloat("rotation", 0.0f, 359.0f);
        animator = ObjectAnimator.ofPropertyValuesHolder(mImageView, pv1, pv2);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(3000L);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!mBoolean){
                    return;
                }
                if (!NetworkUtils.isHaveNetWork) {
                    DialogUtils.showDialog(context, R.string.dialog_setting_title, R.string.dialog_setting_content,
                            R.string.dialog_setting_confirm, R.string.dialog_setting_logout,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UIHelper.forwardNetWorkSetting(WelcomeActivity.this);
                                }
                            }
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CoreUtils.exitApp(context);
                                }
                            }
                    );
                } else {
                    UIHelper.forwardMain(context);
                    finish();
                }
            }
        });
        animator.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.NETWORK_QUEST_CODE) {
            if(mBoolean){
                animator.start();
            }
        }
    }

    @Override
    public void onBackPressed() {
        context.stopService(new Intent(context, NetWorkStateService.class));
        mBoolean = false;
        finish();
    }
}
