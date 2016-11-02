package com.joy.oschina.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.joy.baselib.adapter.CusAdapter;
import com.joy.baselib.adapter.ViewHolder;
import com.joy.baselib.util.L;
import com.joy.baselib.util.SPUtils;
import com.joy.baselib.widget.CircleImageView;
import com.joy.oschina.R;
import com.joy.oschina.bean.ThemeColor;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class DialogGridAdapter extends CusAdapter<ThemeColor> {

    //sss
    private int checkItem;

    public DialogGridAdapter(Context context, List list, int itemLayoutRes) {
        super(context, list, itemLayoutRes);
    }

    @Override
    public View getCustomView(int position, View itemView) {
        CircleImageView imageView = ViewHolder.get(itemView, R.id.civ_color_selector);
        ImageView done = ViewHolder.get(itemView, R.id.done);
        ThemeColor themeColor = list.get(position);
        imageView.setImageResource(themeColor.getColor());
        if (themeColor.isSelected()) {
            done.setVisibility(View.VISIBLE);
        } else {
            done.setVisibility(View.GONE);
        }
        return itemView;
    }

    public int getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(int checkItem) {
        this.checkItem = checkItem;
    }
}
