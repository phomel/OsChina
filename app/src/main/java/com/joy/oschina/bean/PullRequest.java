package com.joy.oschina.bean;

import java.io.Serializable;

public class PullRequest implements Serializable {

    private int iid;

    private String title;

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
