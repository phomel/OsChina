package com.joy.oschina.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.joy.baselib.util.L;
import com.joy.oschina.R;
import com.joy.oschina.base.BaseTabFragment;
import com.joy.oschina.util.AppConstant;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MineFragment extends BaseTabFragment {
    @Override
    protected void initLists(List<Fragment> fragmentList, List<String> titleList) {

        titleList.add(getString(R.string.txt_dynamic));
        titleList.add(getString(R.string.txt_project));
        titleList.add(getString(R.string.txt_star));
        titleList.add(getString(R.string.txt_watch));

        Bundle bundle = getArguments();
        userId = bundle.getInt(AppConstant.USER_ID);

        fragmentList.add(DynamicFragment.newInstance(userId));
        fragmentList.add(SupportFragment.newInstance(AppConstant.TYPE_PROJECT, userId));
        fragmentList.add(SupportFragment.newInstance(AppConstant.TYPE_STAR, userId));
        fragmentList.add(SupportFragment.newInstance(AppConstant.TYPE_WATCH, userId));
    }

    //用户ID
    private int userId;

    /**
     * 创建Fragment实例
     *
     * @param userId
     * @return
     */
    public static Fragment newInstance(int userId) {
        Fragment fragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.USER_ID, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            int position = mTabLayout.getSelectedTabPosition();
            fragmentList.get(position).setUserVisibleHint(true);
        }
    }

}
