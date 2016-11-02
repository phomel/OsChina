package com.joy.oschina.bean;

public class Language {

    /**
     * created_at : 2013-08-01T22:39:56+08:00
     * detail : null
     * id : 5
     * ident : Java
     * name : Java
     * order : 4
     * parent_id : 1
     * projects_count : 21986
     * updated_at : 2013-08-01T22:39:56+08:00
     */

    private String created_at;
    private String detail;
    private int id;
    private String ident;
    private String name;
    private int order;
    private int parent_id;
    private int projects_count;
    private String updated_at;

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public void setProjects_count(int projects_count) {
        this.projects_count = projects_count;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDetail() {
        return detail;
    }

    public int getId() {
        return id;
    }

    public String getIdent() {
        return ident;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getParent_id() {
        return parent_id;
    }

    public int getProjects_count() {
        return projects_count;
    }

    public String getUpdated_at() {
        return updated_at;
    }

}
