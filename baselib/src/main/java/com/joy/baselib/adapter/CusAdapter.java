package com.joy.baselib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.joy.baselib.util.L;

import java.util.List;

/**
 * Adapter基类
 * Created by Administrator on 2016/10/12.
 */
public abstract class CusAdapter<E> extends BaseAdapter {

    protected Context context;
    protected LayoutInflater inflater;
    protected List<E> list;
    //item布局资源
    protected int itemLayoutRes;

    public CusAdapter(Context context, List<E> list, int itemLayoutRes) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.itemLayoutRes = itemLayoutRes;
    }

    /**
     * 设置数据
     * @param list
     */
    public void setData(List<E> list) {
        if(this.list!=null && !this.list.isEmpty()){
            this.list.clear();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 添加列表数据
     * @param list
     */
    public void addAll(List<E> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置添加列表数据
     * @param location
     * @param data
     */
    public void addAll(int location, List<E> data){
        this.list.addAll(location, data);
        notifyDataSetChanged();
    }

    /**
     * 移除指定的List数据
     * @param list
     */
    public void removeAll(List<E> list){
        this.list.removeAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public E getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(itemLayoutRes, parent, false);
        }
        return getCustomView(position, convertView);
    }

    /**
     * 获取视图
     * @param position
     * @param itemView
     * @return
     */
    public abstract View getCustomView(int position, View itemView);

}
