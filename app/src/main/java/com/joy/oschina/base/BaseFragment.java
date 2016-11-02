package com.joy.oschina.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/10/14.
 */
public abstract class BaseFragment extends Fragment {

    protected Activity activity;
    protected View rootView;

    @Override
    public void onAttach(Context context) {
        activity = getActivity();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = getRootView(inflater, container, savedInstanceState);
            initView(rootView);
        } else {
            /**
             * rootView不为空,判断父布局是否为空,不为空移除所有父布局
             */
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeAllViewsInLayout();
            }
        }
        return rootView;
    }

    /**
     * 子类实现,具体页面视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 查找控件(findviewbyid)
     *
     * @param view
     */
    public abstract void initView(View view);

}
