package com.joy.oschina.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joy.baselib.image.ImageLoader;
import com.joy.baselib.util.CoreUtils;
import com.joy.baselib.util.SPUtils;
import com.joy.baselib.util.SnackbarUtils;
import com.joy.baselib.util.file.SerializableUtils;
import com.joy.baselib.widget.CircleImageView;
import com.joy.oschina.R;
import com.joy.oschina.adapter.DialogGridAdapter;
import com.joy.oschina.application.OsChinaApplication;
import com.joy.oschina.base.BaseActivity;
import com.joy.oschina.base.BaseFragment;
import com.joy.oschina.bean.Member;
import com.joy.oschina.bean.ThemeColor;
import com.joy.oschina.ui.fragment.DiscoverFragment;
import com.joy.oschina.ui.fragment.LanguageFragment;
import com.joy.oschina.ui.fragment.MineFragment;
import com.joy.oschina.ui.fragment.ShakeFragment;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/14.
 */
public class MainPageActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation_view_left)
    protected NavigationView mNavigationView;

    private long logout = 0L;
    private Fragment[] fragmentArray = new Fragment[4];

    @BindView(R.id.main_content)
    protected LinearLayout mainLayout;
    private AlertDialog alertDialog;
    private List<ThemeColor> list;
    private boolean isDrawerRight;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView mLogin;
    private CircleImageView mImageView;
    private LoginBroadcastReceiver loginBroadcastReceiver;
    private int index = 0;
    private boolean menuClick = false;
    private boolean ivHeadClick = false;
    private NavigationChangeBroadcastReceiver navReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        //初始化toolbar
        initToolbar();
        mToolbar.setTitle(R.string.menu_item_discover);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                if (menuClick) {
                    showFragment(OsChinaApplication.menuItem);
                }
                if (ivHeadClick) {
                    if (OsChinaApplication.isLogin) {
                        UIHelper.forwardPersonalCenter(activity);
                    } else {
                        UIHelper.forwardLogin(context);
                    }
                    ivHeadClick = false;
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置自定义侧滑栏图标--白色
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.mipmap.action_menu);
        //设置监听,设置返回键监听事件覆盖actionbardrawertoggle打开关闭drawer的功能
        //不覆盖则报错, 是由于actionbardrawertoggle没有打开关闭右边drawer的功能
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchDrawer();
            }
        });
        mDrawerToggle.syncState();

        initNavigationView();
        chooseNavigationView();

        //默认的fragment
        showFragment(mNavigationView.getMenu().findItem(R.id.nav_discover));

        //注册broadcastreceiver
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        navReceiver = new NavigationChangeBroadcastReceiver();
        manager.registerReceiver(navReceiver,
                new IntentFilter(AppConstant.NAV_RECEIVER));
        loginBroadcastReceiver = new LoginBroadcastReceiver();
        LocalBroadcastManager.getInstance(context).registerReceiver(loginBroadcastReceiver,
                new IntentFilter(AppConstant.LOGIN_RECEIVER));

        //初始化dialog的list
        list = initColorList();
    }

    private void initNavigationView() {

        View mHeadView = mNavigationView.getHeaderView(0);
        mLogin = (TextView) mHeadView.findViewById(R.id.btn_forward_login);
        mImageView = (CircleImageView) mHeadView.findViewById(R.id.circle_imageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭侧滑栏再跳转,解决卡顿问题
                ivHeadClick = true;
                switchDrawer();
            }
        });

        initLoginStatus();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                menuClick = true;
                itemClick(item);
                switchDrawer();
                OsChinaApplication.menuItem = item;
                return true;
            }

        });

    }

    private void initLoginStatus() {
        boolean isAutoLogin = (boolean) SPUtils.get(context, AppConstant.IS_AUTO_LOGIN, false);
        //判断是否由主题传递的登录状态
        boolean isLogin = false;
        if (SerializableUtils.getSerializable(AppConstant.THEME_INTENT) != null) {
            isLogin = SerializableUtils.getSerializable(AppConstant.THEME_INTENT);
        }

        //自动登录,判断是否在checkbox被钩中登陆过
        if (isAutoLogin) {
            if ((Boolean) SPUtils.get(context, AppConstant.IS_LOGIN, false)) {
                if (SerializableUtils.getSerializable(AppConstant.SAVE_INSTANCE) != null) {
                    OsChinaApplication.member = SerializableUtils.getSerializable(AppConstant.SAVE_INSTANCE);
                    mLogin.setText(OsChinaApplication.member.getName());
                    ImageLoader.getInstance(context).loadImage(OsChinaApplication.member.getNew_portrait(), mImageView);
                    OsChinaApplication.isLogin = true;
                    SerializableUtils.setSerializable(AppConstant.THEME_INTENT, false);
                }
            } else {
                OsChinaApplication.isLogin = false;
            }
        }

        //改变主题前是否登录过
        if (isLogin) {
            if (SerializableUtils.getSerializable(AppConstant.SAVE_INSTANCE) != null) {
                OsChinaApplication.member = SerializableUtils.getSerializable(AppConstant.SAVE_INSTANCE);
                mLogin.setText(OsChinaApplication.member.getName());
                ImageLoader.getInstance(context).loadImage(OsChinaApplication.member.getNew_portrait(), mImageView);
                OsChinaApplication.isLogin = true;
                SerializableUtils.setSerializable(AppConstant.THEME_INTENT, false);
            } else {
                OsChinaApplication.isLogin = false;
            }
        }
    }

    /**
     * 菜单点击
     *
     * @param item
     */
    private void itemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_discover: //发现
                index = 0;
                break;
            case R.id.nav_mine: //我的
                index = 1;
                break;
            case R.id.nav_language: //语言
                index = 2;
                break;
            case R.id.nav_push_one_time: //摇一摇
                index = 3;
                break;
            case R.id.nav_translate_theme: //切换主题
                index = 4;
                break;
            case R.id.nav_setting: //设置
                index = 5;
                break;
        }
        if (index == 0 || index == 1 || index == 2 || index == 3) {
            mToolbar.setTitle(item.getTitle().toString());
        }
    }

    private void showFragment(MenuItem item) {
        menuClick = false;
        Fragment fragment = null;
        if (index == 0 || index == 1 || index == 2 || index == 3) {
            fragment = fragmentArray[index];
        }
        if (fragment == null) {
            switch (item.getItemId()) {
                case R.id.nav_discover:
                    if (fragmentArray[0] == null) {
                        fragmentArray[0] = new DiscoverFragment();
                    }
                    //switchFragment(R.id.main_content, fragmentArray[0]);
                    // mToolbar.setTitle(getString(R.string.menu_item_discover));
                    break;
                case R.id.nav_mine:
//                    if (OsChinaApplication.isLogin == false) {
//                        UIHelper.forwardLogin(activity);
//                    } else {
                    if (OsChinaApplication.isLogin) {
                        if (fragmentArray[1] == null) {
                            fragmentArray[1] = MineFragment.newInstance(OsChinaApplication.member.getId());
                        }
                    }
                    // switchFragment(R.id.main_content, fragmentArray[1]);
                    //mToolbar.setTitle(getString(R.string.menu_item_mine));
                    // }
                    break;
                case R.id.nav_language:
                    if (fragmentArray[2] == null) {
                        fragmentArray[2] = new LanguageFragment();
                    }
                    //switchFragment(R.id.main_content, fragmentArray[2]);
                    //mToolbar.setTitle(getString(R.string.menu_item_language));
                    break;
                case R.id.nav_push_one_time:
                    if (fragmentArray[3] == null) {
                        fragmentArray[3] = new ShakeFragment();
                    }
                    //switchFragment(R.id.main_content, fragmentArray[3]);
                    //mToolbar.setTitle(getString(R.string.menu_item_push_one_time));
                    break;
                case R.id.nav_translate_theme:
                    selectColor();
                    break;
                case R.id.nav_setting:
                    UIHelper.forwardSetting(context);
                    break;
            }
        }
        if (index == 0 || index == 1 || index == 2 || index == 3) {
            fragment = fragmentArray[index];
            if (index == 1 && !OsChinaApplication.isLogin) {
                UIHelper.forwardLogin(activity);
                return;
            }
            switchFragment(R.id.main_content, fragment);
        }
    }

    public void switchDrawer() {
        isDrawerRight = (boolean) SPUtils.get(context, AppConstant.IS_DRAWER_RIGHT, false);
        if (isDrawerRight) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.END);
            }
        } else {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
    }

    private void selectColor() {
        View dialog = View.inflate(context, R.layout.dialog_view, null);
        GridView grid = (GridView) dialog.findViewById(R.id.grid_view_dialog);
        final int i = (int) SPUtils.get(context, AppConstant.THEME_COLOR, 0);
        list.get(i).setSelected(true);
        final DialogGridAdapter dialogGridAdapter = new DialogGridAdapter(context, list, R.layout.item_grid_dialog);
        grid.setAdapter(dialogGridAdapter);
        alertDialog = new AlertDialog.Builder(context)
                .setTitle(getString(R.string.txt_change_theme))
                .setView(dialog)
                .create();
        alertDialog.show();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (i == position) {
                    alertDialog.dismiss();
                } else {
                    for (ThemeColor color : list) {
                        color.setSelected(false);
                    }
                    ThemeColor themeColor = dialogGridAdapter.getItem(position);
                    themeColor.setSelected(true);
                    SPUtils.put(context, AppConstant.THEME_COLOR, position);
                    dialogGridAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                    if (OsChinaApplication.isLogin) {
                        SerializableUtils.setSerializable(AppConstant.THEME_INTENT, true);
                    } else {
                        SerializableUtils.setSerializable(AppConstant.THEME_INTENT, false);
                    }
                    UIHelper.forwardMain(context);
                    finish();
                }
            }
        });
    }

    private List initColorList() {
        List<ThemeColor> list = new ArrayList<>();
        list.add(getThemeColor(R.color.themeBlue, false));
        list.add(getThemeColor(R.color.themeDarkGray, false));
        list.add(getThemeColor(R.color.themeDarkOrange, false));
        list.add(getThemeColor(R.color.themeDarkBlue, false));
        list.add(getThemeColor(R.color.themeOrange, false));
        list.add(getThemeColor(R.color.themePurple, false));
        list.add(getThemeColor(R.color.themeRed, false));
        list.add(getThemeColor(R.color.themeGreen, false));
        return list;
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            switchDrawer();
            return;
        }
        if (System.currentTimeMillis() - logout > 2000) {
            SnackbarUtils.makeText(context, mainLayout, getString(R.string.snack_tip), Snackbar.LENGTH_SHORT);
            logout = System.currentTimeMillis();
        } else {
            //退出应用
            CoreUtils.exitApp(context);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                UIHelper.forwardSetting(context);
            break;
        }
        return false;
    }

    public ThemeColor getThemeColor(int i, boolean b) {
        ThemeColor themeColor = new ThemeColor();
        themeColor.setColor(i);
        themeColor.setSelected(b);
        return themeColor;
    }

    class NavigationChangeBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            chooseNavigationView();
        }
    }

    private void chooseNavigationView() {
        isDrawerRight = (boolean) SPUtils.get(context, AppConstant.IS_DRAWER_RIGHT, false);
        DrawerLayout.LayoutParams layoutParams =
                (DrawerLayout.LayoutParams) mNavigationView.getLayoutParams();
        if (isDrawerRight) {
            layoutParams.gravity = Gravity.END;
        } else {
            layoutParams.gravity = Gravity.START;
        }
        mNavigationView.setLayoutParams(layoutParams);
    }

    class LoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //登录后改变登录状态
            if (OsChinaApplication.isLogin) {
                if (intent.getSerializableExtra(AppConstant.SAVE_INSTANCE) != null) {
                    OsChinaApplication.member = (Member) intent
                            .getSerializableExtra(AppConstant.SAVE_INSTANCE);
                    mLogin.setText(OsChinaApplication.member.getName());
                    ImageLoader.getInstance(context)
                            .loadImage(OsChinaApplication.member.getNew_portrait(), mImageView);
                }
            } else {
                mLogin.setText(R.string.txt_click_to_login);
                mImageView.setImageResource(R.mipmap.mini_avatar);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //finsh返回默认页面
        MenuItem item = mNavigationView.getMenu().findItem(R.id.nav_discover);
        if (requestCode == 0) {
            if (OsChinaApplication.isLogin) {
                showFragment(OsChinaApplication.menuItem);
            } else {
                itemClick(item);
                showFragment(item);
                item.setChecked(true);
            }
        }
        if (requestCode == 1) {
            if (!OsChinaApplication.isLogin) {
                itemClick(item);
                showFragment(item);
                item.setChecked(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(navReceiver);
        LocalBroadcastManager.getInstance(context).unregisterReceiver(loginBroadcastReceiver);
        super.onDestroy();
    }
}
