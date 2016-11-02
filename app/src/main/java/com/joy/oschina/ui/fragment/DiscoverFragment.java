package com.joy.oschina.ui.fragment;

import android.support.v4.app.Fragment;

import com.joy.oschina.R;
import com.joy.oschina.base.BaseTabFragment;
import com.joy.oschina.network.RequestAPI;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
public class DiscoverFragment extends BaseTabFragment {

    @Override
    protected void initLists(List<Fragment> fragmentList, List<String> titleList) {

        titleList.add(getString(R.string.tab_support_project));
        titleList.add(getString(R.string.tab_hot_project));
        titleList.add(getString(R.string.tab_recently_updated));
        fragmentList.add(SupportFragment.newInstance(RequestAPI.FEATURED));
        fragmentList.add(SupportFragment.newInstance(RequestAPI.POPULAR));
        fragmentList.add(SupportFragment.newInstance(RequestAPI.LATEST));
    }

}
