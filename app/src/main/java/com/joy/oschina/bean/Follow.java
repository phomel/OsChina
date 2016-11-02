package com.joy.oschina.bean;

import java.io.Serializable;

/**
 * Created by sks on 2016/4/18.
 */
public class Follow implements Serializable{

    private int followers;

    private int starred;

    private int following;

    private int watched;

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getStarred() {
        return starred;
    }

    public void setStarred(int starred) {
        this.starred = starred;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

}
