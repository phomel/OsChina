package com.joy.oschina.bean;

import java.io.Serializable;

/**
 * 作者
 * Created by sks on 2016/4/18.
 */
public class Author implements Serializable{

    //姓名
    private String name;

    //邮箱
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
