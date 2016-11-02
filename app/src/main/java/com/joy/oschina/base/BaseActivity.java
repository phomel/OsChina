package com.joy.oschina.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joy.baselib.util.CoreUtils;
import com.joy.baselib.util.SPUtils;
import com.joy.baselib.util.file.AppDataManager;
import com.joy.baselib.util.file.SerializableUtils;
import com.joy.oschina.R;
import com.joy.oschina.application.OsChinaApplication;
import com.joy.oschina.ui.activity.MainPageActivity;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.ThemeUtils;
import com.nostra13.universalimageloader.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/14.
 */
public class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected Toolbar mToolbar;
    protected Activity activity;

    //上一个fragment
    protected Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.changeTheme(this);
        super.onCreate(savedInstanceState);
        CoreUtils.addAppActivity(this);
        context = this;
        activity = this;
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.base_toolbar);
        mToolbar.setNavigationIcon(R.mipmap.nav_back);
        mToolbar.setTitleTextColor(context.getResources().getColor(R.color.textWhite));
        mToolbar.setTitle("");
        mToolbar.setSubtitleTextColor(context.getResources().getColor(R.color.textWhite));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CoreUtils.removeAppActivity(this);
    }

    /**
     * 显示fragment,比较上一个fragment和下一个fragment,
     *
     * @param res          容器
     * @param nextFragment 下一个fragment
     */
    protected void switchFragment(int res, Fragment nextFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mFragment != null) {
            if (mFragment != nextFragment) {
                if (!nextFragment.isAdded()) {
                    transaction.hide(mFragment).add(res, nextFragment,
                            nextFragment.getClass().getName());
                } else {
                    transaction.hide(mFragment).show(nextFragment);
                }
            }
        } else {
            transaction.add(res, nextFragment,
                    nextFragment.getClass().getName());
        }
        //推荐用此方法提交事务,方便安全
        transaction.commitAllowingStateLoss();
        mFragment = nextFragment;
    }
}
