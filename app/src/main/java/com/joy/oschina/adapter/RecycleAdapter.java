package com.joy.oschina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joy.oschina.R;
import com.joy.oschina.bean.Language;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>{

    private Context context;
    private List<Language> languageList;

    public RecycleAdapter(Context context, List<Language> languageList) {
        this.context = context;
        this.languageList = languageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_recycle_view_language, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Language language = languageList.get(position);
        holder.mLanguage.setText(language.getName());
        holder.mTheme.setText(language.getProjects_count() + context.getString(R.string.txt_number_of_theme));
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mLanguage;
        TextView mTheme;

        public MyViewHolder(View itemView) {
            super(itemView);
            mLanguage = (TextView) itemView.findViewById(R.id.tv_language);
            mTheme = (TextView) itemView.findViewById(R.id.tv_theme);
        }
    }

}
