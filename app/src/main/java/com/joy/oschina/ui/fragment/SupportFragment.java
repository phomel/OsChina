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
import com.joy.oschina.adapter.ExploreAdapter;
import com.joy.oschina.base.BaseFragment;
import com.joy.oschina.bean.Dynamic;
import com.joy.oschina.bean.Featured;
import com.joy.oschina.comparator.FeatureComparator;
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
 * Created by Administrator on 2016/10/14.
 */
public class SupportFragment extends BaseFragment implements RefreshLayout.OnLoadListener,
        RefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.lv)
    ListView mListView;

    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;

    private int page = 1;
    private String url;
    private ExploreAdapter adapter;
    private Map<String, String> params;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_listview, container, false);
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        mRefreshLayout.setOnLoadListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Featured featured = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                intent.putExtra(AppConstant.INTENT_FEATURED, featured);
                startActivity(intent);
            }
        });
        Bundle bundle = getArguments();
        params = new HashMap<>();
        if (bundle.getInt(AppConstant.USER_ID) == 0) {
            String uri = bundle.getString("url");
            url = RequestAPI.getUrl(uri);
            params.put("page", String.valueOf(page));
        } else {
            int userId = bundle.getInt(AppConstant.USER_ID);
            String uri = bundle.getString("url");
            url = RequestAPI.getMineUrl(uri, userId);
        }
    }

    protected void loadDiscover(final LoadType loadType) {
        showProgress(loadType, progressLayout);
        RequestManager.getInstance().get(url, new RequestManager.ResponseListener() {
            @Override
            public void onResponse(String response) {
                progressLayout.setVisibility(View.GONE);
                L.e("返回数据===========" + response);
                List<Featured> list = JSONParseUtils.parseArray(response, Featured.class);
                Collections.sort(list, new FeatureComparator());
                if (list == null || list.isEmpty()) {
                    return;
                }
                switch (loadType) {
                    case REFRESH:
                        if (list == null || list.isEmpty()) {
                            return;
                        }
                        List<Featured> tempList = new ArrayList<Featured>();
                        Featured mFeatured = adapter.getItem(0);
                        for (Featured featured : list) {
                            if (featured.getCreated_at().getTime() > mFeatured.getCreated_at().getTime()) {
                                tempList.add(featured);
                            }
                        }
                        if (tempList != null && !tempList.isEmpty()) {
                            adapter.addAll(0, tempList);
                            SnackbarUtils.makeText(getActivity(), mRefreshLayout, getString(R.string.snack_refresh_tip, tempList.size()),
                                    Snackbar.LENGTH_SHORT);
                        }
                        mRefreshLayout.setRefreshing(false);
                        break;
                    case LOAD:
                        if((list == null || list.isEmpty()) || list.size()!=20){
                            mRefreshLayout.completePageData();
                            return;
                        }
                        page++;
                        adapter.addAll(list);
                        //隐藏上拉加载布局
                        mRefreshLayout.setLoading(false);
                        break;
                    case FIRST:
                        if(list == null || list.isEmpty()) {
                            return;
                        }
                        page++;
                        adapter = new ExploreAdapter(activity, list,
                                R.layout.item_list_fragment_support);
                        mListView.setAdapter(adapter);
                        break;
                }
            }
        }, new RequestManager.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
            }
        }, params);
    }

    public static Fragment newInstance(String url){
        Fragment fragment = new SupportFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment newInstance(String url, int userId){
        Fragment fragment = new SupportFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.USER_ID, userId);
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
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

    @Override
    public void onPageLoad() {
        loadDiscover(LoadType.LOAD);
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadDiscover(LoadType.REFRESH);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && adapter == null && activity != null) {
            loadDiscover(LoadType.FIRST);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUserVisibleHint(getUserVisibleHint());
    }
}
