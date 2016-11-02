package com.joy.oschina.util;

import android.content.Context;

import com.joy.oschina.R;
import com.joy.oschina.bean.Dynamic;

/**
 * 动态解析工具类
 */
public class DynamicUtils {

    //动态的类型
    public final static byte EVENT_TYPE_CREATED = 0x1;      // 创建了issue
    public final static byte EVENT_TYPE_UPDATED = 0x2;      // 更新项目
    public final static byte EVENT_TYPE_CLOSED = 0x3;       // 关闭项目
    public final static byte EVENT_TYPE_REOPENED = 0x4;     // 重新打开了项目
    public final static byte EVENT_TYPE_PUSHED = 0x5;       // push
    public final static byte EVENT_TYPE_COMMENTED = 0x6;    // 评论
    public final static byte EVENT_TYPE_MERGED = 0x7;       // 合并
    public final static byte EVENT_TYPE_JOINED = 0x8;       //# User joined project
    public final static byte EVENT_TYPE_LEFT = 0x9;         //# User left project
    public final static byte EVENT_TYPE_FORKED = 0xb;       // fork了项目

    private static String getDynamicTitle(Dynamic event) {
        String title = "";
        if (event.getEvents().getIssue() != null) {
            title = " #" + event.getEvents().getIssue().getIid();
        }
        if (event.getEvents().getPull_request() != null) {
            title = " #" + event.getEvents().getPull_request().getIid();
        }
        return title;
    }

    public static String parseDynamicTitle(Context context, String author_name, String pAuthor_And_pName, Dynamic event) {
        author_name = "(" + author_name + ")";
        pAuthor_And_pName = "<" + pAuthor_And_pName + ">";
        String title = "";
        String eventTitle = "";
        int action = event.getAction();
        switch (action) {
            case EVENT_TYPE_CREATED:// 创建了issue
                eventTitle = event.getTarget_type() + getDynamicTitle(event);
                title = context.getString(R.string.in_project_title) + pAuthor_And_pName + context.getString(R.string.create_project_title) + eventTitle;
                break;
            case EVENT_TYPE_UPDATED:// 更新项目
                title = context.getString(R.string.update_project_title) + pAuthor_And_pName;
                break;
            case EVENT_TYPE_CLOSED:  //关闭项目
                eventTitle = event.getTarget_type() + getDynamicTitle(event);
                title = context.getString(R.string.close_project_title) + pAuthor_And_pName + " -- " + eventTitle;
                break;
            case EVENT_TYPE_REOPENED://重新打开了项目
                eventTitle = event.getTarget_type() + getDynamicTitle(event);
                title = context.getString(R.string.reopen_project_title) + pAuthor_And_pName + " -- " + eventTitle;
                break;
            case EVENT_TYPE_PUSHED:// push
                eventTitle = event.getData().getRef()
                        .substring(event.getData().getRef().lastIndexOf("/") + 1);
                title = context.getString(R.string.pull_project_title) + pAuthor_And_pName
                        + " -- " + eventTitle + context.getString(R.string.branch_title);
                break;
            case EVENT_TYPE_COMMENTED:// 评论
                if (event.getEvents().getIssue() != null) {
                    eventTitle = "Issues";
                } else if (event.getEvents().getPull_request() != null) {
                    eventTitle = "PullRequest";
                }
                eventTitle = eventTitle + getDynamicTitle(event);
                title = context.getString(R.string.comment_project_title) + pAuthor_And_pName + " -- " + eventTitle;
                break;
            case EVENT_TYPE_MERGED:// 合并
                eventTitle = event.getTarget_type() + getDynamicTitle(event);
                title = context.getString(R.string.accept_project_title) + pAuthor_And_pName + " -- " + eventTitle;
                break;
            case EVENT_TYPE_JOINED:// # User joined project
                title = context.getString(R.string.enter_project_title) + pAuthor_And_pName;
                break;
            case EVENT_TYPE_LEFT:// # User left project
                title = context.getString(R.string.leave_project_title) + pAuthor_And_pName;
                break;
            case EVENT_TYPE_FORKED:// fork了项目
                title = context.getString(R.string.fork_project_title) + pAuthor_And_pName;
                break;
            default:
                title = context.getString(R.string.update_project_default_title);
                break;
        }
        title = author_name + " " + title;
        return title;
    }

}
