package com.joy.oschina.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.joy.oschina.R;
import com.joy.oschina.base.BaseActivity;
import com.joy.oschina.bean.Featured;
import com.joy.oschina.ui.fragment.MineFragment;
import com.joy.oschina.util.ThemeUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/22.
 */
public class DynamicOthersActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_others);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initToolbar();
        Featured featured = getIntent().getParcelableExtra("user");
        mToolbar.setTitle(featured.getOwner().getName());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = MineFragment.newInstance(featured.getOwner().getId());
        transaction.add(R.id.line_other_content, fragment);
        transaction.commitAllowingStateLoss();
    }
}
