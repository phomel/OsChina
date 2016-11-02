package com.joy.oschina.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joy.baselib.adapter.CusAdapter;
import com.joy.baselib.adapter.ViewHolder;
import com.joy.baselib.image.ImageLoader;
import com.joy.baselib.util.DateUtils;
import com.joy.baselib.widget.CircleImageView;
import com.joy.oschina.R;
import com.joy.oschina.bean.Author;
import com.joy.oschina.bean.Dynamic;
import com.joy.oschina.bean.Owner;
import com.joy.oschina.util.DynamicUtils;
import com.joy.oschina.util.TypefaceUtils;

import java.util.List;

/**
 * 发现列表的Adapter
 * Created by Administrator on 2016/10/17.
 */
public class DynamicAdapter extends CusAdapter<Dynamic> {

    public DynamicAdapter(Context context, List<Dynamic> list, int itemLayoutRes) {
        super(context, list, itemLayoutRes);
    }

    @Override
    public View getCustomView(int position, View itemView) {
        //头像
        CircleImageView portraitView = ViewHolder.get(itemView, R.id.iv_dynamic_portrait);
        //标题
        TextView titleView = ViewHolder.get(itemView, R.id.tv_dynamic_title);
        //描述
        LinearLayout allCommintsListView = ViewHolder.get(itemView, R.id.event_all_commits_list);
        //时间
        TextView timeView = ViewHolder.get(itemView, R.id.tv_dynamic_time);

        final Dynamic event = getItem(position);
        //标题
//        Dynamic.AuthorEntity author = event.getAuthor();
//        String titleName ="";
//        if(author!=null){
//            String userName = author.getUsername();;
//            titleName += userName;
//        }
//        if(!TextUtils.isEmpty(titleName)){
//            titleName +="/";
//        }
//        titleName+=event.getProject().getOwner().getName();
//
//        String title = "(" + author.getName() + ")" +"推送到了项目<" + titleName + ">--"
//                + event.getProject().getName();

        String title = DynamicUtils.parseDynamicTitle(context, event
                .getAuthor().getName(), event.getProject().getOwner().getName()
                + "/" + event.getProject().getName(), event);
        titleView.setText(title);

        //描述
        allCommintsListView.setVisibility(View.GONE);
        allCommintsListView.removeAllViews();
        if (event.getData() != null) {
            List<Dynamic.DataEntity.CommitsEntity> commits = event.getData().getCommits();
            if (commits != null && commits.size() > 0) {
                showCommitInfo(allCommintsListView, commits);
                allCommintsListView.setVisibility(View.VISIBLE);
            }
        }

        //时间
        timeView.setText(DateUtils.getUpdateTime(event.getUpdated_at()));

        //头像
        String portraitURL = event.getAuthor().getNew_portrait();
        if (portraitURL.endsWith("portrait.gif")) {
            portraitView.setImageResource(R.mipmap.mini_avatar);
        } else {
            ImageLoader.getInstance(context).loadImage(portraitURL, portraitView,
                    R.mipmap.mini_avatar, R.mipmap.mini_avatar);
        }
        return itemView;
    }

    /**
     * 显示提交信息
     *
     * @param layout
     * @param commits
     */
    private void showCommitInfo(LinearLayout layout, List<Dynamic.DataEntity.CommitsEntity> commits) {
        if (commits.size() >= 2) {
            addCommitItem(layout, commits.get(0));
            addCommitItem(layout, commits.get(1));
        } else {
            for (Dynamic.DataEntity.CommitsEntity commit : commits) {
                addCommitItem(layout, commit);
            }
        }
    }

    /**
     * 添加commit项
     *
     * @param layout
     * @param commit
     */
    private void addCommitItem(LinearLayout layout, Dynamic.DataEntity.CommitsEntity commit) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_event_item_commits, null);
        String str = commit.getId();
        int length = 9;
        String string = str.substring(str.length() - length, str.length());
        ((TextView) view.findViewById(R.id.event_commits_listitem_commitid))
                .setText(string);
        if (commit.getAuthor() != null) {
            ((TextView) view.findViewById(R.id.event_commits_listitem_username))
                    .setText(commit.getAuthor().getName());
        }else {
            ((TextView) view.findViewById(R.id.event_commits_listitem_username))
                    .setText("");
        }
        ((TextView) view.findViewById(R.id.event_commits_listitem_message))
                .setText(commit.getMessage());
        layout.addView(view);
    }
}
