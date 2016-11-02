package com.joy.oschina.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joy.baselib.network.RequestManager;
import com.joy.baselib.util.JSONParseUtils;
import com.joy.baselib.util.L;
import com.joy.baselib.util.SPUtils;
import com.joy.baselib.util.T;
import com.joy.baselib.util.file.SerializableUtils;
import com.joy.oschina.R;
import com.joy.oschina.application.OsChinaApplication;
import com.joy.oschina.base.BaseActivity;
import com.joy.oschina.bean.Member;
import com.joy.oschina.network.RequestAPI;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.ThemeUtils;
import com.joy.oschina.util.UIHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/18.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText mUserName;

    @BindView(R.id.et_pwd)
    EditText mPwd;

    @BindView(R.id.btn_login)
    Button mLogin;

    @BindView(R.id.text_input_username)
    TextInputLayout usernameTextInput;

    @BindView(R.id.text_input_pwd)
    TextInputLayout pwdTextInput;

    @BindView(R.id.chk_login)
    CheckBox mCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        initToolbar();
        mToolbar.setTitle(R.string.btn_login);
        ButterKnife.bind(this);

        boolean isAutoLogin = (boolean) SPUtils.get(context, AppConstant.IS_AUTO_LOGIN, false);
        mCheckBox.setChecked(isAutoLogin);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(context, AppConstant.IS_AUTO_LOGIN, isChecked);
            }
        });

//        mUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            String et = null;
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (v.hasFocus()) {
////                    textView1.setVisibility(View.VISIBLE);
//                    et = ((EditText)v).getHint().toString();
//                    ((EditText)v).setHint("");
//                } else {
////                    textView1.setVisibility(View.GONE);
//                    ((EditText)v).setHint(et);
//                }
//            }
//        });
    }

    /**
     * 登录操作
     */
    private void login() {
        final String username = mUserName.getText().toString();
        final String pwd = mPwd.getText().toString();
        if (TextUtils.isEmpty(username)) {
            usernameTextInput.setError(getString(R.string.txt_username_not_be_null));
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            pwdTextInput.setError(getString(R.string.txt_pwd_not_be_null));
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("email", username);
        params.put("password", pwd);

        RequestManager.getInstance().post(RequestAPI.getUrl(RequestAPI.LOGIN), new RequestManager.ResponseListener() {
            @Override
            public void onResponse(String response) {
                final Member member = JSONParseUtils.parseObject(response, Member.class);
                new AsyncTask<String, Void, Member>() {

                    @Override
                    protected void onPreExecute() {
                        ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage(getString(R.string.dialog_progress_login__tip));
                        progressDialog.show();
                    }

                    @Override
                    protected Member doInBackground(String... strings) {
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return member;
                    }

                    @Override
                    protected void onPostExecute(Member member1) {
                        OsChinaApplication.isLogin = true;
                        if (mCheckBox.isChecked()) {
                            SPUtils.put(context, AppConstant.IS_LOGIN, true);
                        } else {
                            SPUtils.put(context, AppConstant.IS_LOGIN, false);
                        }
                        SerializableUtils.setSerializable(AppConstant.SAVE_INSTANCE, member);
                        Intent intent = new Intent(AppConstant.LOGIN_RECEIVER);
                        intent.putExtra(AppConstant.SAVE_INSTANCE, member1);
                        LocalBroadcastManager.getInstance(context)
                                .sendBroadcast(intent);
                        finish();
                    }
                }.execute();
            }
        }, new RequestManager.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                T.showShort(context, R.string.toast_login_error_tip);
                return;
            }
        }, params);
    }

    public void forwardWeb(View view) {
        UIHelper.forwardProblemWeb(context, context.getString(R.string.txt_web_os_china), "http://git.oschina.net");
    }

    @OnClick(R.id.btn_login)
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }
}
