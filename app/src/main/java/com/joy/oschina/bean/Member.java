package com.joy.oschina.bean;

import java.io.Serializable;

/**
 * 会员
 * Created by sks on 2016/4/18.
 */
public class Member implements Serializable{

    //ID
    private int id;

    //用户名
    private String username;

    //姓名
    private String name;

    //简介
    private String bio;

    //email
    private String email;

    //微博
    private String weibo;

    //博客
    private String blog;

    //头像
    private String new_portrait;

    //关注
    private Follow follow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getNew_portrait() {
        return new_portrait;
    }

    public void setNew_portrait(String new_portrait) {
        this.new_portrait = new_portrait;
    }

    public Follow getFollow() {
        return follow;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }

}
