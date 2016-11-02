package com.joy.oschina.ui.fragment;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.joy.baselib.image.ImageLoader;
import com.joy.baselib.network.RequestManager;
import com.joy.baselib.util.JSONParseUtils;
import com.joy.baselib.util.T;
import com.joy.oschina.R;
import com.joy.oschina.base.BaseFragment;
import com.joy.oschina.bean.Featured;
import com.joy.oschina.bean.Owner;
import com.joy.oschina.network.RequestAPI;
import com.joy.oschina.ui.activity.ProjectDetailActivity;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.ShakeManager;
import com.joy.oschina.util.TypefaceUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 摇一摇
 * Created by Administrator on 2016/10/21.
 */
public class ShakeFragment extends BaseFragment {

    @BindView(R.id.rl_shake_Up)
    RelativeLayout shakeUpLayout;

    @BindView(R.id.rl_shake_down)
    RelativeLayout shakeDownLayout;

    @BindView(R.id.shake_loading)
    LinearLayout shakeLoadingLayout;

    @BindView(R.id.shake_project)
    RelativeLayout shakeProjectLayout;

    @BindView(R.id.iv_portrait)
    ImageView imgView;

    @BindView(R.id.tv_title)
    TextView titleView;

    @BindView(R.id.tv_description)
    TextView descView;

    @BindView(R.id.tv_watch)
    TextView watchView;

    @BindView(R.id.tv_star)
    TextView startView;

    @BindView(R.id.tv_fork)
    TextView forkView;

    @BindView(R.id.tv_language)
    TextView languageView;

    @BindView(R.id.forward_project_detail)
    LinearLayout linearLayout;

    private final int DURATION_TIME = 600;

    private Map<Integer, Integer> soundPoolMap = new HashMap<Integer, Integer>();

    private SoundPool soundPool;

    private ShakeManager shakeManager;

    private Featured project;

    @Override
    public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shake, container, false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            shakeManager.stop();  //停止监听
        } else {
            shakeManager.start();  //启动监听
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        shakeManager.stop();  //停止监听
    }

    @Override
    public void onResume() {
        super.onResume();
        shakeManager.start();  //启动监听
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
        loadSound();
        shakeManager = new ShakeManager(activity);
        shakeManager.setShakeListener(new ShakeManager.ShakeListener() {
            @Override
            public void onShake() {
                //开始摇一摇
                startAnim();
            }
        });
    }

    /**
     * 加载声音
     */
    private void loadSound() {
        soundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    soundPoolMap.put(0, soundPool.load(activity.getAssets().
                            openFd("shake_sound_male.mp3"), 1));
                    soundPoolMap.put(1, soundPool.load(activity.getAssets().
                            openFd("shake_match.mp3"), 1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        ;
    }


    /**
     * 执行动画
     */
    private void startAnim() {
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                -0.5f);
        mytranslateanimup0.setDuration(DURATION_TIME);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                +0.5f);
        mytranslateanimup1.setDuration(DURATION_TIME);
        mytranslateanimup1.setStartOffset(DURATION_TIME);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        shakeUpLayout.startAnimation(animup);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                +0.5f);
        mytranslateanimdn0.setDuration(DURATION_TIME);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                -0.5f);
        mytranslateanimdn1.setDuration(DURATION_TIME);
        mytranslateanimdn1.setStartOffset(DURATION_TIME);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        shakeDownLayout.startAnimation(animdn);

        mytranslateanimup0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                shakeProjectLayout.setVisibility(View.GONE);
                shakeManager.stop();
                //播放声音
                soundPool.play(soundPoolMap.get(0), (float) 0.2, (float) 0.2, 0, 0, (float) 0.6);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadProject();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 加载项目
     */
    private void loadProject() {
        shakeLoadingLayout.setVisibility(View.VISIBLE);

        RequestManager.getInstance().get(RequestAPI.getUrl(RequestAPI.SHAKE), new RequestManager.ResponseListener() {
            @Override
            public void onResponse(String response) {
                project = JSONParseUtils.parseObject(response, Featured.class);
                shakeLoadingLayout.setVisibility(View.GONE);
                soundPool.play(soundPoolMap.get(1), (float) 0.2, (float) 0.2, 0, 0, (float) 0.6);
                shakeManager.start();

                if (project != null) {
                    showProject();
                }
            }
        }, new RequestManager.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                T.showShort(getActivity(), R.string.toast_load_defeat);
            }
        }, null);
    }

    /**
     * 显示项目
     */
    private void showProject() {
        //标题
        Owner ownerVo = project.getOwner();
        String titleName = "";
        if (ownerVo != null) {
            String userName = ownerVo.getUsername();
            ;
            titleName += userName;
        }
        if (!TextUtils.isEmpty(titleName)) {
            titleName += "/";
        }
        titleName += project.getName();
        titleView.setText(titleName);

        String description = project.getDescription();
        try {
            String description2 = new String(description.getBytes("ISO-8859-1"), "UTF-8");
            if (TextUtils.isEmpty(description2)) {
                description2 = getString(R.string.no_description_hint);
            }
            descView.setText(description2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        TypefaceUtils.setIconText(watchView, getString(R.string.sem_watch) + " " + project.getWatches_count());
        TypefaceUtils.setIconText(startView, getString(R.string.sem_star) + " " + project.getStars_count());
        TypefaceUtils.setIconText(forkView, getString(R.string.sem_fork) + " " + project.getForks_count());

        String language = project.getLanguage();
        if (TextUtils.isEmpty(language)) {
            languageView.setVisibility(View.GONE);
        } else {
            TypefaceUtils.setIconText(languageView, getString(R.string.sem_tag) + " " + project.getLanguage());
        }

        String portraitURL = project.getOwner().getNew_portrait();
        if (portraitURL.endsWith("portrait.gif")) {
            imgView.setImageResource(R.mipmap.mini_avatar);
        } else {
            ImageLoader.getInstance(getActivity()).loadImage(portraitURL, imgView);
        }
        shakeProjectLayout.setVisibility(View.VISIBLE);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                intent.putExtra(AppConstant.INTENT_FEATURED, project);
                intent.putExtra(AppConstant.SHARK, "shark");
                startActivity(intent);
            }
        });
    }

}
