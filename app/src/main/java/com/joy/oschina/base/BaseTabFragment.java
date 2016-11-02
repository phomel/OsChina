package com.joy.oschina.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joy.baselib.util.L;
import com.joy.oschina.R;
import com.joy.oschina.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/14.
 */
public  abstract class BaseTabFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    protected TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    protected ViewPager mViewPager;

    protected List<String> titleList = new ArrayList<>();
    protected List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_tab, container, false);
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
        initLists(fragmentList, titleList);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),
                titleList, fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0, true);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 重写该方法再次切换时会再次加载数据
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            int position = mTabLayout.getSelectedTabPosition();
            fragmentList.get(position).setUserVisibleHint(true);
        }
    }

    protected abstract void initLists(List<Fragment> fragmentList, List<String> titleList);
}
