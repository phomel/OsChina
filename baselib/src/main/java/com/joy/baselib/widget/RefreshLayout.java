package com.joy.baselib.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;
import com.joy.baselib.R;

/**
 * 自定义控件：下拉刷新,上拉加载
 * Created by sks on 2016/4/9.
 */
public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {

    //最小滑动距离
    private int mTouchSlop;

    //ListView的加载中footer
    private View mListViewFooter;

    //按下时的y坐标
    private int mYDown;

    //抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
    private int mLastY;

    //是否完成分页加载
    private boolean iscompleteLoading=false;

    //上拉监听器, 到了最底部的上拉加载操作
    private OnLoadListener mOnLoadListener;

    //是否在加载中(上拉加载更多)
    private boolean isLoading = false;

    /**
     * listview实例
     */
    private ListView mListView;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.common_listview_footer, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN: //按下
                mYDown = (int)ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE: //移动
                mLastY = (int)ev.getRawY();
                break;
            case MotionEvent.ACTION_UP: //抬起
                if(canLoad()){
                    loadData();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否可加载更多
     * @return
     */
    private boolean canLoad() {
        return isPullUp() && !isLoading && isBottom();
    }

    /**
     * 判断是否到了ListView底部
     * @return
     */
    private boolean isBottom(){
        if(mListView!=null && mListView.getAdapter()!=null){
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount()-1);
        }
        return false;
    }

    /**
     * 是否是上拉操作
     * @return
     */
    private boolean isPullUp(){
       return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 加载数据
     */
    private void loadData(){
        if(iscompleteLoading){   //完成加载，不需要再进行分页加载数据
            return;
        }

        if(mOnLoadListener!=null){
            setLoading(true);
            mOnLoadListener.onPageLoad();
        }
    }

    /**
     * 是否显示分页加载,true显示,
     * @param loading
     */
    public void setLoading(boolean loading) {
        this.isLoading = loading;
        if(isLoading){
            mListView.addFooterView(mListViewFooter);
        }else{
            mListView.removeFooterView(mListViewFooter);
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * 设置完成加载
     * @param iscompleteLoading
     */
    public void setIscompleteLoading(boolean iscompleteLoading) {
        this.iscompleteLoading = iscompleteLoading;
    }

    /**
     * 完成分页加载
     */
    public void completePageData() {
        setLoading(false);
        iscompleteLoading=true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 滚动时到了最底部也可以加载更多
        if (canLoad()) {
            loadData();
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    /**
     * 加载更多的监听器,加载下一页
     * @author mrsimple
     */
    public interface OnLoadListener {
        void onPageLoad();
    }

}
