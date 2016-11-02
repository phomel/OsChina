package com.joy.oschina.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.joy.baselib.network.RequestManager;
import com.joy.baselib.util.JSONParseUtils;
import com.joy.baselib.util.L;
import com.joy.baselib.util.SnackbarUtils;
import com.joy.baselib.widget.RefreshLayout;
import com.joy.oschina.R;
import com.joy.oschina.adapter.DynamicAdapter;
import com.joy.oschina.base.BaseFragment;
import com.joy.oschina.bean.Dynamic;
import com.joy.oschina.bean.Featured;
import com.joy.oschina.bean.Owner;
import com.joy.oschina.comparator.DynamicComparator;
import com.joy.oschina.network.RequestAPI;
import com.joy.oschina.ui.activity.ProjectDetailActivity;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.LoadType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/17.
 */
public class DynamicFragment extends BaseFragment implements RefreshLayout.OnLoadListener,
        RefreshLayout.OnRefreshListener{

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.lv)
    ListView mListView;

    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;

    private int userId;
    private int page = 1;
    private DynamicAdapter adapter;

    public static Fragment newInstance(int userId){
        Fragment fragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.USER_ID, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_listview,container,false);
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadListener(this);

        Bundle bundle = getArguments();
        userId = bundle.getInt(AppConstant.USER_ID);

    }

    /**
     * 加载数据
     */
    private void loadData(final LoadType loadType){
        //显示加载进度条
        showProgress(loadType, progressLayout);
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        String url = new StringBuffer(RequestAPI.getUrl(RequestAPI.DYNAMIC)).append(userId).toString();
        L.e("---------------" + url);
        RequestManager.getInstance().get(url, new RequestManager.ResponseListener() {
            @Override
            public void onResponse(String response) {
                //隐藏加载进度条
                progressLayout.setVisibility(View.GONE);
                List<Dynamic> list = JSONParseUtils.parseArray(response, Dynamic.class);
                Collections.sort(list, new DynamicComparator());
                L.e("---------------" + response);
                switch (loadType) {
                    case REFRESH: //下拉刷新
                        if(list == null || list.isEmpty()) {
                            return;
                        }
                        List<Dynamic> tempList = new ArrayList<Dynamic>();
                        Dynamic dynamic = adapter.getItem(0);
                        for(Dynamic dyna: list) {
                            if(dyna.getCreated_at().getTime()>dynamic.getCreated_at().getTime()){
                                tempList.add(dyna);
                            }
                        }

                        if(tempList != null && !tempList.isEmpty()) {
                            //下拉刷新数据更新
                            adapter.addAll(0, tempList);
                            SnackbarUtils.makeText(getActivity(), mRefreshLayout,
                                    getString(R.string.snack_refresh_tip, tempList.size()),
                                    Snackbar.LENGTH_SHORT);
                        }
                        //下拉刷新动画结束
                        mRefreshLayout.setRefreshing(false);
                        break;
                    case LOAD: //上拉加载
                        if((list == null || list.isEmpty()) || list.size()!=20){
                            mRefreshLayout.completePageData();
                            return;
                        }
                        page++;
                        adapter.addAll(list);
                        //隐藏上拉加载布局
                        mRefreshLayout.setLoading(false);
                        break;
                    case FIRST: //第一次加载
                        if(list == null || list.isEmpty()) {
                            return;
                        }
                        page++;
                        adapter = new DynamicAdapter(activity, list,
                                R.layout.item_list_dynamic);
                        mListView.setAdapter(adapter);
                        break;
                }

            }
        }, new RequestManager.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //隐藏加载进度条
                progressLayout.setVisibility(View.GONE);
            }
        }, params);
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData(LoadType.REFRESH);
    }

    @Override
    public void onPageLoad() {
        loadData(LoadType.LOAD);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && adapter == null && activity != null) {
            loadData(LoadType.FIRST);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUserVisibleHint(getUserVisibleHint());
    }

    private void showProgress(LoadType loadType, View progressLayout) {
        switch (loadType) {
            case REFRESH:
                progressLayout.setVisibility(View.GONE);
                break;
            case LOAD:
                progressLayout.setVisibility(View.GONE);
                break;
            case FIRST:
                progressLayout.setVisibility(View.VISIBLE);
                break;
        }
    }
}
