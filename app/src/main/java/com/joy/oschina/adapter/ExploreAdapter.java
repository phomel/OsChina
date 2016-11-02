package com.joy.oschina.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.joy.baselib.adapter.CusAdapter;
import com.joy.baselib.adapter.ViewHolder;
import com.joy.baselib.image.ImageLoader;
import com.joy.baselib.widget.CircleImageView;
import com.joy.oschina.bean.Featured;

import java.util.List;

import com.joy.oschina.R;
import com.joy.oschina.bean.Owner;
import com.joy.oschina.ui.activity.DynamicOthersActivity;
import com.joy.oschina.ui.activity.ProjectDetailActivity;
import com.joy.oschina.util.AppConstant;
import com.joy.oschina.util.TypefaceUtils;

/**
 * 发现列表的Adapter
 * Created by Administrator on 2016/10/17.
 */
public class ExploreAdapter extends CusAdapter<Featured> {

    public ExploreAdapter(Context context, List<Featured> list, int itemLayoutRes) {
        super(context, list, itemLayoutRes);
    }

    @Override
    public View getCustomView(final int position, View itemView) {
        //头像
        CircleImageView portraitView = ViewHolder.get(itemView, R.id.iv_portrait);
        //标题
        TextView titleView = ViewHolder.get(itemView, R.id.tv_title);
        //描述
        TextView descriptionView = ViewHolder.get(itemView, R.id.tv_description);
        //浏览次数
        TextView watchView = ViewHolder.get(itemView, R.id.tv_watch);
        //星级
        TextView starView = ViewHolder.get(itemView, R.id.tv_star);
        //分支
        TextView forkView = ViewHolder.get(itemView, R.id.tv_fork);
        //语言
        TextView languageView = ViewHolder.get(itemView, R.id.tv_language);

        final Featured project = getItem(position);
        //标题
        Owner ownerVo = project.getOwner();
        String titleName = "";
        if (ownerVo != null) {
            String userName = ownerVo.getUsername();
            ;
            titleName += userName;
        }
        if (!TextUtils.isEmpty(titleName)) {
            titleName += "/";
        }
        titleName += project.getName();
        titleView.setText(titleName);

        //描述
        String description = project.getDescription();
        if (TextUtils.isEmpty(description)) {
            description = context.getString(R.string.no_description_hint);
        }
        descriptionView.setText(description);

        TypefaceUtils.setIconText(watchView, context.getString(R.string.sem_watch) + " " + project.getWatches_count());
        TypefaceUtils.setIconText(starView, context.getString(R.string.sem_star) + " " + project.getStars_count());
        TypefaceUtils.setIconText(forkView, context.getString(R.string.sem_fork) + " " + project.getForks_count());
        //语言
        String language = project.getLanguage();
        if (TextUtils.isEmpty(language)) {
            languageView.setVisibility(View.GONE);
        } else {
            TypefaceUtils.setIconText(languageView, context.getString(R.string.sem_tag) + " " + project.getLanguage());
        }

        //头像
        String portraitURL = project.getOwner().getNew_portrait();
        if (portraitURL.endsWith("portrait.gif")) {
            portraitView.setImageResource(R.mipmap.mini_avatar);
        } else {
            ImageLoader.getInstance(context).loadImage(portraitURL, portraitView,
                    R.mipmap.mini_avatar, R.mipmap.mini_avatar);
        }
        portraitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Featured featured = list.get(position);
                Intent intent = new Intent(context, DynamicOthersActivity.class);
                intent.putExtra("user", featured);
                context.startActivity(intent);
            }
        });
        return itemView;
    }

}
