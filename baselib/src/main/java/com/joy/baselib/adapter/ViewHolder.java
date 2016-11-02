package com.joy.baselib.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder类
 * Created by Administrator on 2016/10/12.
 */
public class ViewHolder {

    /**
     * 根据控件id获取控件
     * @param view
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if(view.getTag() == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }

        View childView = viewHolder.get(id);
        if(childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T)childView;
    }

}
