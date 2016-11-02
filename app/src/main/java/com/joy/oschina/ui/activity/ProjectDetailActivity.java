package com.joy.oschina.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.joy.baselib.image.ImageLoader;
import com.joy.baselib.network.RequestManager;
import com.joy.baselib.util.DateUtils;
import com.joy.baselib.util.L;
import com.joy.oschina.R;
import com.joy.oschina.base.BaseActivity;
import com.joy.oschina.bean.Featured;
import com.joy.oschina.bean.Owner;
import com.joy.oschina.network.RequestAPI;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.ThemeUtils;
import com.joy.oschina.util.TypefaceUtils;
import com.joy.oschina.util.UIHelper;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/22.
 */
public class ProjectDetailActivity extends BaseActivity {

    @BindView(R.id.project_detail_title)
    TextView projectTitle;

    @BindView(R.id.project_detail_update_time)
    TextView projectUpdate;

    @BindView(R.id.project_detail_describe)
    TextView projectDescribe;

    @BindView(R.id.project_detail_watched)
    TextView projectWatched;

    @BindView(R.id.project_detail_stared)
    TextView projectStared;

    @BindView(R.id.project_detail_how_long)
    TextView projectLong;

    @BindView(R.id.project_detail_share)
    TextView projectShare;

    @BindView(R.id.project_detail_docker)
    TextView projectDocker;

    @BindView(R.id.project_detail_auth)
    TextView projectAuth;
    private Featured project;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        project = getIntent().getParcelableExtra(AppConstant.INTENT_FEATURED);
        Owner ownerVo = project.getOwner();

        initToolbar();
        mToolbar.setTitle(project.getName());

        String str = getIntent().getStringExtra(AppConstant.SHARK);

        projectTitle.setText(project.getName());
        if (project.getLast_push_at() != null) {
            projectUpdate.setText(getString(R.string.txt_update_in_time) +
                    DateUtils.getUpdateTime(project.getLast_push_at()));
        } else if (project.getCreated_at() != null){
            projectUpdate.setText(getString(R.string.txt_update_in_time) +
                    DateUtils.getUpdateTime(project.getCreated_at()));
        } else {
            projectUpdate.setText(R.string.txt_update_time);
        }

        //描述
        String description = project.getDescription();
        if (TextUtils.isEmpty(description)) {
            description = context.getString(R.string.no_description_hint);
        }

        if (str == null) {
            projectDescribe.setText(description);
        } else {
            try {
                String description2 = new String(description.getBytes("ISO-8859-1"), "UTF-8");
                projectDescribe.setText(description2);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        TypefaceUtils.setIconText(projectWatched, context.getString(R.string.sem_watch)
                + getString(R.string.txt_icon_watch) + project.getWatches_count());
        TypefaceUtils.setIconText(projectStared, context.getString(R.string.sem_star)
                + getString(R.string.txt_icon_star) + project.getStars_count());
        TypefaceUtils.setIconText(projectShare, context.getString(R.string.sem_fork)
                + "  " + project.getForks_count());
        //语言
        String language = project.getLanguage();
        if (TextUtils.isEmpty(language)) {
            projectDocker.setVisibility(View.GONE);
        } else {
            TypefaceUtils.setIconText(projectDocker, context.getString(R.string.sem_tag)
                    + "  " + project.getLanguage());
        }

        //作者和时间

        TypefaceUtils.setIconText(projectLong, context.getString(R.string.fa_clock_o) + "  "
                 + DateUtils.getUpdateTime(project.getCreated_at()));

        if (str == null) {
            TypefaceUtils.setIconText(projectAuth, context.getString(R.string.sem_user) + "  "
                    + ownerVo.getName());
            mToolbar.setSubtitle(ownerVo.getName());
        } else {
            try {
                String name = new String(ownerVo.getName().getBytes("ISO-8859-1"), "UTF-8");
                TypefaceUtils.setIconText(projectAuth, context.getString(R.string.sem_user) + "  "
                        + name);
                mToolbar.setSubtitle(name);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void forwardReadme(View view) {
        String readmeURL = RequestAPI.getReadmeURL(project.getId());
        String url = RequestAPI.getUrl(readmeURL);
        Map map = new HashMap();
        RequestManager.getInstance().get(url, new RequestManager.ResponseListener() {
            @Override
            public void onResponse(String response) {
                L.e("-------------" + response);
            }
        }, new RequestManager.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, map);
        UIHelper.forwardProblemWeb(context, project.getName(), url);
    }
}
