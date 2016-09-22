package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Info extends Entity implements Parcelable{
    private long timeStamp;//新闻的唯一标示，相当于Id
    private String title;//新闻标题
    private String releaseTime;//发布时间
    private String creator;
    private String image;//图片地址
    private Boolean isShow;
    private Boolean isRecommend;
    private Boolean isTop;
    private String type;
    private int total;

    public Info(){}

    protected Info(Parcel in) {
        timeStamp = in.readLong();
        title = in.readString();
        releaseTime = in.readString();
        creator = in.readString();
        image = in.readString();
        type = in.readString();
        total = in.readInt();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(timeStamp);
        dest.writeString(title);
        dest.writeString(releaseTime);
        dest.writeString(creator);
        dest.writeString(image);
        dest.writeString(type);
        dest.writeInt(total);
    }

    @Override
    public String toString() {
        return title+image+releaseTime;
    }
}

