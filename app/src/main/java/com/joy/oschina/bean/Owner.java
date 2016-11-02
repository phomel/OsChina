package com.joy.oschina.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Owner implements Parcelable {

    private int id;//358131,
    private String username;//"yuanhack",
    private String email;//"yuanhack@163.com",
    private String name;//"yuanhack",
    private String state;//"active",
    private Date created_at;//"2015-04-08T14:25:34+08:00",
    private String portrait;//"uploads/31/358131_yuanhack.jpg?1428477009",
    private String new_portrait;//"http://git.oschina.n

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNew_portrait() {
        return new_portrait;
    }

    public void setNew_portrait(String new_portrait) {
        this.new_portrait = new_portrait;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", created_at=" + created_at +
                ", portrait='" + portrait + '\'' +
                ", new_portrait='" + new_portrait + '\'' +
                '}';
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.name);
        dest.writeString(this.state);
        dest.writeLong(created_at != null ? created_at.getTime() : -1);
        dest.writeString(this.portrait);
        dest.writeString(this.new_portrait);
    }

    public Owner() {
    }

    protected Owner(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.email = in.readString();
        this.name = in.readString();
        this.state = in.readString();
        long tmpCreated_at = in.readLong();
        this.created_at = tmpCreated_at == -1 ? null : new Date(tmpCreated_at);
        this.portrait = in.readString();
        this.new_portrait = in.readString();
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        public Owner createFromParcel(Parcel source) {
            return new Owner(source);
        }

        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

}
