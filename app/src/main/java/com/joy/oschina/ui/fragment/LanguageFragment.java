package com.joy.oschina.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.joy.baselib.network.RequestManager;
import com.joy.baselib.util.JSONParseUtils;
import com.joy.baselib.util.L;
import com.joy.baselib.util.SPUtils;
import com.joy.oschina.R;
import com.joy.oschina.adapter.RecycleAdapter;
import com.joy.oschina.application.OsChinaApplication;
import com.joy.oschina.base.BaseFragment;
import com.joy.oschina.bean.Language;
import com.joy.oschina.network.RequestAPI;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.LoadType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/18.
 */
public class LanguageFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;

    private int page = 1;
    private RecycleAdapter adapter;
    private boolean isCardView;
    private LocalBroadcastManager mManager;
    private LanguageBroadcastReceiver languageBroadcastReceiver;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void initView(View view) {

        mManager = LocalBroadcastManager.getInstance(getActivity());
        languageBroadcastReceiver = new LanguageBroadcastReceiver();
        IntentFilter filter = new IntentFilter(AppConstant.CARD_RECEIVER);
        mManager.registerReceiver(languageBroadcastReceiver, filter);
        ButterKnife.bind(this, view);
        switchLayoutManager();
        initData();
    }

    private void switchLayoutManager() {
        isCardView = (boolean) SPUtils.get(OsChinaApplication.context, AppConstant.IS_CARD_VIEW, false);
        if (isCardView) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private void initData() {
        progressLayout.setVisibility(View.VISIBLE);
        Map<String,String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        RequestManager.getInstance().get(RequestAPI.getUrl(RequestAPI.LANGUAGE), new RequestManager.ResponseListener() {
            @Override
            public void onResponse(String response) {
                L.e("返回数据===========" + response);
                progressLayout.setVisibility(View.GONE);
                List<Language> list = JSONParseUtils.parseArray(response, Language.class);
                if (list == null || list.isEmpty()) {
                    return;
                }
                //必须在得到list后setAdapter,不然一开始加载数据默认是空的,必须更新数据
                adapter = new RecycleAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);
            }
        }, new RequestManager.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
            }
        }, map);
    }

    class LanguageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switchLayoutManager();
        }
    }

    @Override
    public void onDestroy() {
        mManager.unregisterReceiver(languageBroadcastReceiver);
        super.onDestroy();
    }
}
