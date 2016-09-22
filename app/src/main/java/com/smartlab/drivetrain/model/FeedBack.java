package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/12/29.
 * 投诉列表
 */
public class FeedBack implements Parcelable{
    private String id;          //  投诉编号
    private String type;        //  投诉类型
    private int grade;          //  投诉等级
    private String content;     //  投诉的内容
    private String imageUrl;    //  图片的地址
    private String feedback;    //  反馈
    private String complainTime;//  提交时间
    private String feedbackTime;//  反馈时间
    private boolean isHandled;  //  是否已被处理
    private String name;        //  被投诉的驾校
    private String answer;
    public FeedBack(){
    }


    protected FeedBack(Parcel in) {
        id = in.readString();
        type = in.readString();
        grade = in.readInt();
        content = in.readString();
        imageUrl = in.readString();
        feedback = in.readString();
        complainTime = in.readString();
        feedbackTime = in.readString();
        isHandled = in.readByte() != 0;
        name = in.readString();
        answer = in.readString();
    }

    public static final Creator<FeedBack> CREATOR = new Creator<FeedBack>() {
        @Override
        public FeedBack createFromParcel(Parcel in) {
            return new FeedBack(in);
        }

        @Override
        public FeedBack[] newArray(int size) {
            return new FeedBack[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getComplainTime() {
        return complainTime;
    }

    public void setComplainTime(String complainTime) {
        this.complainTime = complainTime;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public boolean isHandled() {
        return isHandled;
    }

    public void setIsHandled(boolean isHandled) {
        this.isHandled = isHandled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeInt(grade);
        dest.writeString(content);
        dest.writeString(imageUrl);
        dest.writeString(feedback);
        dest.writeString(complainTime);
        dest.writeString(feedbackTime);
        dest.writeByte((byte) (isHandled ? 1 : 0));
        dest.writeString(name);
        dest.writeString(answer);
    }
}
