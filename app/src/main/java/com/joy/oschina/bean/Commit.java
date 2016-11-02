package com.joy.oschina.bean;

import java.io.Serializable;

/**
 * Created by sks on 2016/4/18.
 */
public class Commit implements Serializable{

    private String id;

    private String message;

    private String url;

    private Author author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
