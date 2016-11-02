package com.joy.oschina.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.joy.baselib.util.SPUtils;
import com.joy.baselib.util.T;
import com.joy.baselib.util.file.AppDataManager;
import com.joy.oschina.R;
import com.joy.oschina.base.BaseActivity;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.ThemeUtils;
import com.joy.oschina.util.UIHelper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/18.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_flush_cache)
    TextView cacheSize;

    @BindView(R.id.chk_isCardView)
    CheckBox mCheckBox;

    @BindView(R.id.switch_isDrawerRight)
    Switch mSwitch;

    private String str;

    @BindView(R.id.lin_flush_cache_title)
    LinearLayout flushCache;

    private ProgressDialog progressDialog;
    private LocalBroadcastManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        initToolbar();
        mToolbar.setTitle(R.string.setting);

        manager = LocalBroadcastManager.getInstance(context);

        setCacheSize();
        boolean isCardView = (boolean) SPUtils.get(context, AppConstant.IS_CARD_VIEW, false);
        mCheckBox.setChecked(isCardView);

        boolean isDrawerRight = (boolean) SPUtils.get(context, AppConstant.IS_DRAWER_RIGHT, false);
        mSwitch.setChecked(isDrawerRight);
        /**
         * 清理缓存
         */
        flushCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cacheSize.getText().equals(getString(R.string.tv_zero_kb))) {
                    T.showShort(context, getString(R.string.tv_over_more_tip_flush));
                } else {
                    new AsyncTask<String, Void, String>() {

                        @Override
                        protected void onPreExecute() {
                            progressDialog = new ProgressDialog(context);
                            progressDialog.setMessage(getString(R.string.dialog_flushing_cache));
                            progressDialog.show();
                        }

                        @Override
                        protected String doInBackground(String... strings) {
                            //耗时操作
                            try {
                                Thread.sleep(2000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            AppDataManager.cleanCache(context);
                            setCacheSize();
                            progressDialog.dismiss();
                        }
                    }.execute();
                }
            }
        });

        /**
         * 设置卡片布局
         */
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(context, AppConstant.IS_CARD_VIEW, isChecked);
                manager.sendBroadcast(new Intent(AppConstant.CARD_RECEIVER));
            }
        });

        /**
         * 设置navigationview是否右展开
         */
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(context, AppConstant.IS_DRAWER_RIGHT, isChecked);
                manager.sendBroadcast(new Intent(AppConstant.NAV_RECEIVER));
            }
        });
    }

    private void setCacheSize() {
        try {
            str = AppDataManager.getCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cacheSize.setText(str);
    }

    /**
     * 跳转到意见反馈
     *
     * @param view
     */
    public void forwardProblemWeb(View view) {
        UIHelper.forwardProblemWeb(context, context.getString(R.string.txt_web_os_china), "http://git.oschina.net");
    }
}
