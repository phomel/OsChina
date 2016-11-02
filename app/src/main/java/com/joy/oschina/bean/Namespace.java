package com.joy.oschina.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Namespace implements Parcelable {

    private Date created_at;//2015-04-08T14:25:34+08:00",
    private String description;//",
    private int id;//51316,
    private String name;//yuanhack",
    private int owner_id;//": 358131,
    private String path;//yuanhack",
    private Date updated_at;//2015-04-08T14:25:34+08

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Namespace{" +
                "created_at=" + created_at +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", owner_id=" + owner_id +
                ", path='" + path + '\'' +
                ", updated_at=" + updated_at +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(created_at != null ? created_at.getTime() : -1);
        dest.writeString(this.description);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.owner_id);
        dest.writeString(this.path);
        dest.writeLong(updated_at != null ? updated_at.getTime() : -1);
    }

    public Namespace() {
    }

    protected Namespace(Parcel in) {
        long tmpCreated_at = in.readLong();
        this.created_at = tmpCreated_at == -1 ? null : new Date(tmpCreated_at);
        this.description = in.readString();
        this.id = in.readInt();
        this.name = in.readString();
        this.owner_id = in.readInt();
        this.path = in.readString();
        long tmpUpdated_at = in.readLong();
        this.updated_at = tmpUpdated_at == -1 ? null : new Date(tmpUpdated_at);
    }

    public static final Creator<Namespace> CREATOR = new Creator<Namespace>() {
        public Namespace createFromParcel(Parcel source) {
            return new Namespace(source);
        }

        public Namespace[] newArray(int size) {
            return new Namespace[size];
        }
    };

}
