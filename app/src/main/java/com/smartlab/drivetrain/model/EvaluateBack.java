package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/12/29.
 *
 */
public class EvaluateBack implements Parcelable {
    private long id;
    private String drivingName;
    private String coachName;
    private int km;
    private int grade;
    private String content;
    private String imageUrl;
    private String evaluateTime;

    public EvaluateBack(){}
    protected EvaluateBack(Parcel in) {
        id = in.readLong();
        drivingName = in.readString();
        coachName = in.readString();
        km = in.readInt();
        grade = in.readInt();
        content = in.readString();
        imageUrl = in.readString();
        evaluateTime = in.readString();
    }

    public static final Creator<EvaluateBack> CREATOR = new Creator<EvaluateBack>() {
        @Override
        public EvaluateBack createFromParcel(Parcel in) {
            return new EvaluateBack(in);
        }

        @Override
        public EvaluateBack[] newArray(int size) {
            return new EvaluateBack[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDrivingName() {
        return drivingName;
    }

    public void setDrivingName(String drivingName) {
        this.drivingName = drivingName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
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

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    @Override
    public String toString() {
        return "EvaluateBack{" +
                "id=" + id +
                ", drivingName='" + drivingName + '\'' +
                ", coachName='" + coachName + '\'' +
                ", km=" + km +
                ", grade=" + grade +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", evaluateTime='" + evaluateTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(drivingName);
        dest.writeString(coachName);
        dest.writeInt(km);
        dest.writeInt(grade);
        dest.writeString(content);
        dest.writeString(imageUrl);
        dest.writeString(evaluateTime);
    }
}
