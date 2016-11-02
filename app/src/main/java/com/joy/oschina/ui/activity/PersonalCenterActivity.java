package com.joy.oschina.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.joy.baselib.image.ImageLoader;
import com.joy.baselib.util.DialogUtils;
import com.joy.baselib.util.SPUtils;
import com.joy.baselib.util.file.SerializableUtils;
import com.joy.baselib.widget.CircleImageView;
import com.joy.oschina.R;
import com.joy.oschina.application.OsChinaApplication;
import com.joy.oschina.base.BaseActivity;
import com.joy.oschina.bean.Follow;
import com.joy.oschina.bean.Member;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.ThemeUtils;
import com.joy.oschina.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/21.
 */
public class PersonalCenterActivity extends BaseActivity {

    @BindView(R.id.civ_personal)
    CircleImageView head;

    @BindView(R.id.member_name)
    TextView name;

    @BindView(R.id.member_introduce)
    TextView introduce;

    @BindView(R.id.member_email)
    TextView email;

    @BindView(R.id.member_wei_bo)
    TextView weiBo;

    @BindView(R.id.member_bo_ke)
    TextView boKe;

    @BindView(R.id.member_followers)
    TextView followers;

    @BindView(R.id.member_following)
    TextView following;

    @BindView(R.id.member_watched)
    TextView watched;

    @BindView(R.id.member_stared)
    TextView stared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        initView();
    }

    private void initView() {
        initToolbar();
        mToolbar.setTitle(R.string.txt_title_personal_center);
        ButterKnife.bind(this);
        initMember();
    }

    private void initMember() {
        Member member = (Member) getIntent().getSerializableExtra(AppConstant.SAVE_INSTANCE);
        ImageLoader.getInstance(context).loadImage(member.getNew_portrait(), head);
        name.setText(member.getName());
        if (member.getBio() == null) {
            introduce.setText(getString(R.string.txt_personal_introduce) + getString(R.string.txt_none_for_instance));
        } else {
            introduce.setText(getString(R.string.txt_personal_introduce) + member.getBio());
        }
        email.setText(getString(R.string.txt_email) + member.getEmail());
        if (member.getWeibo() == null) {
            weiBo.setText(getString(R.string.txt_wei_bo) + getString(R.string.txt_none_for_instance));
        } else {
            weiBo.setText(getString(R.string.txt_wei_bo) + member.getWeibo());
        }
        if (member.getBlog() == null) {
            boKe.setText(getString(R.string.txt_bo_ke) + getString(R.string.txt_none_for_instance));
        } else {
            boKe.setText(getString(R.string.txt_bo_ke) + member.getBlog());
        }
        Follow follow = member.getFollow();
        followers.setText(getString(R.string.txt_followers) + follow.getFollowers());
        following.setText(getString(R.string.txt_following) + follow.getFollowing());
        watched.setText(getString(R.string.txt_watched) + follow.getWatched());
        stared.setText(getString(R.string.txt_personal_introduce) + follow.getStarred());
    }

    public void forwardLogin(View view) {
        DialogUtils.showDialog(context, R.string.btn_cancel_login, R.string.dialog_logout_tip,
                R.string.dialog_tip_confirm, R.string.dialog_tip_nav,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SerializableUtils.deleteSerializable(AppConstant.SAVE_INSTANCE);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(AppConstant.LOGIN_RECEIVER));
                        UIHelper.forwardLogin(context);
                        OsChinaApplication.isLogin = false;
                        finish();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }
}
