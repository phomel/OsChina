package com.joy.oschina.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.joy.oschina.R;
import com.joy.oschina.base.BaseActivity;
import com.joy.oschina.util.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/19.
 */
public class WebActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initToolbar();
        String toolTitle = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        mToolbar.setTitle(toolTitle);

        //webView
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new MyWebViewClient());
        //设置可以显示javascript
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
