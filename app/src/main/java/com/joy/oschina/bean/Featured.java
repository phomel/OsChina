package com.joy.oschina.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Featured implements Parcelable {

    private int id;//514717,
    private String name;//"sarudp",
    private String description;//"sarudp 是 SYN-ACK-Retransfer UDP的缩写，是增加了传输可靠性的 UDP 协议。\r\n\r\n    在 UDP 基础上实现了请求重传和应答重传。\r\n\r\nsarudp 同时具有 UDP 和 TCP 的优点，可应用于各种模式的开发，并且可同时支持\r\nIPv6 和 IPv4 应用程序的开发。",
    private String default_branch;//"master",
//    private String public;//true,
    private String path;//"sarudp",
    private String path_with_namespace;//"yuanhack/sarudp",
    private boolean issues_enabled;//true,
    private boolean pull_requests_enabled;//true,
    private boolean wiki_enabled;//true,
    private Date created_at;//"2015-08-20T18:15:07+08:00",
    private Date last_push_at;//"2015-10-14T12:00:17+08:00",
    private String parent_id;//null,
//    private String fork?;//false,
    private int forks_count;//0,
    private int stars_count;//0,
    private int watches_count;//1,
    private String language;//"C",
    private String paas;//null,
    private boolean stared;//null,
    private boolean watched;//null,
    private String relation;//null,
    private int recomm;//1,
    private String parent_path_with_namespace;//null
    private Owner owner;
    private Namespace namespace;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefault_branch() {
        return default_branch;
    }

    public void setDefault_branch(String default_branch) {
        this.default_branch = default_branch;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath_with_namespace() {
        return path_with_namespace;
    }

    public void setPath_with_namespace(String path_with_namespace) {
        this.path_with_namespace = path_with_namespace;
    }

    public boolean issues_enabled() {
        return issues_enabled;
    }

    public void setIssues_enabled(boolean issues_enabled) {
        this.issues_enabled = issues_enabled;
    }

    public boolean isPull_requests_enabled() {
        return pull_requests_enabled;
    }

    public void setPull_requests_enabled(boolean pull_requests_enabled) {
        this.pull_requests_enabled = pull_requests_enabled;
    }

    public boolean isWiki_enabled() {
        return wiki_enabled;
    }

    public void setWiki_enabled(boolean wiki_enabled) {
        this.wiki_enabled = wiki_enabled;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getLast_push_at() {
        return last_push_at;
    }

    public void setLast_push_at(Date last_push_at) {
        this.last_push_at = last_push_at;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public int getStars_count() {
        return stars_count;
    }

    public void setStars_count(int stars_count) {
        this.stars_count = stars_count;
    }

    public int getWatches_count() {
        return watches_count;
    }

    public void setWatches_count(int watches_count) {
        this.watches_count = watches_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPaas() {
        return paas;
    }

    public void setPaas(String paas) {
        this.paas = paas;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getRecomm() {
        return recomm;
    }

    public void setRecomm(int recomm) {
        this.recomm = recomm;
    }

    public String getParent_path_with_namespace() {
        return parent_path_with_namespace;
    }

    public void setParent_path_with_namespace(String parent_path_with_namespace) {
        this.parent_path_with_namespace = parent_path_with_namespace;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    @Override
    public String toString() {
        return "Featured{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", default_branch='" + default_branch + '\'' +
                ", path='" + path + '\'' +
                ", path_with_namespace='" + path_with_namespace + '\'' +
                ", issues_enabled=" + issues_enabled +
                ", pull_requests_enabled=" + pull_requests_enabled +
                ", wiki_enabled=" + wiki_enabled +
                ", created_at=" + created_at +
                ", last_push_at=" + last_push_at +
                ", parent_id='" + parent_id + '\'' +
                ", forks_count=" + forks_count +
                ", stars_count=" + stars_count +
                ", watches_count=" + watches_count +
                ", language='" + language + '\'' +
                ", paas='" + paas + '\'' +
                ", stared='" + stared + '\'' +
                ", watched='" + watched + '\'' +
                ", relation='" + relation + '\'' +
                ", recomm=" + recomm +
                ", parent_path_with_namespace='" + parent_path_with_namespace + '\'' +
                ", owner=" + owner +
                ", namespace=" + namespace +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.default_branch);
        dest.writeString(this.path);
        dest.writeString(this.path_with_namespace);
        dest.writeByte(issues_enabled ? (byte) 1 : (byte) 0);
        dest.writeByte(pull_requests_enabled ? (byte) 1 : (byte) 0);
        dest.writeByte(wiki_enabled ? (byte) 1 : (byte) 0);
        dest.writeLong(created_at != null ? created_at.getTime() : -1);
        dest.writeLong(last_push_at != null ? last_push_at.getTime() : -1);
        dest.writeString(this.parent_id);
        dest.writeInt(this.forks_count);
        dest.writeInt(this.stars_count);
        dest.writeInt(this.watches_count);
        dest.writeString(this.language);
        dest.writeString(this.paas);
        dest.writeByte(stared ? (byte) 1 : (byte) 0);
        dest.writeByte(watched ? (byte) 1 : (byte) 0);
        dest.writeString(this.relation);
        dest.writeInt(this.recomm);
        dest.writeString(this.parent_path_with_namespace);
        dest.writeParcelable(this.owner, flags);
        dest.writeParcelable(this.namespace, flags);
    }

    public Featured() {
    }

    protected Featured(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.default_branch = in.readString();
        this.path = in.readString();
        this.path_with_namespace = in.readString();
        this.issues_enabled = in.readByte() != 0;
        this.pull_requests_enabled = in.readByte() != 0;
        this.wiki_enabled = in.readByte() != 0;
        long tmpCreated_at = in.readLong();
        this.created_at = tmpCreated_at == -1 ? null : new Date(tmpCreated_at);
        long tmpLast_push_at = in.readLong();
        this.last_push_at = tmpLast_push_at == -1 ? null : new Date(tmpLast_push_at);
        this.parent_id = in.readString();
        this.forks_count = in.readInt();
        this.stars_count = in.readInt();
        this.watches_count = in.readInt();
        this.language = in.readString();
        this.paas = in.readString();
        this.stared = in.readByte() != 0;
        this.watched = in.readByte() != 0;
        this.relation = in.readString();
        this.recomm = in.readInt();
        this.parent_path_with_namespace = in.readString();
        this.owner = in.readParcelable(Owner.class.getClassLoader());
        this.namespace = in.readParcelable(Namespace.class.getClassLoader());
    }

    public static final Creator<Featured> CREATOR = new Creator<Featured>() {
        public Featured createFromParcel(Parcel source) {
            return new Featured(source);
        }

        public Featured[] newArray(int size) {
            return new Featured[size];
        }
    };

}
