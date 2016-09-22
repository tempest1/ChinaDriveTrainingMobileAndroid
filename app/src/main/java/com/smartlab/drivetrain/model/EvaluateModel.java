package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/12/29.
 * 评价的Model
 */
public class EvaluateModel implements Parcelable{
    private String cmd;
    private long drivingTimestamp;
    private String drivingName;
    private long coachTimestamp1;
    private long coachTimestamp2;
    private String coachName1;
    private String coachName2;
    private boolean res;
    private String event;
    private String error;
    public EvaluateModel(String event,String error){
        this.event = event;
        this.error = error;
    }


    public EvaluateModel(){}

    protected EvaluateModel(Parcel in) {
        cmd = in.readString();
        drivingTimestamp = in.readLong();
        drivingName = in.readString();
        coachTimestamp1 = in.readLong();
        coachTimestamp2 = in.readLong();
        coachName1 = in.readString();
        coachName2 = in.readString();
        res = in.readByte() != 0;
        event = in.readString();
        error = in.readString();
    }

    public static final Creator<EvaluateModel> CREATOR = new Creator<EvaluateModel>() {
        @Override
        public EvaluateModel createFromParcel(Parcel in) {
            return new EvaluateModel(in);
        }

        @Override
        public EvaluateModel[] newArray(int size) {
            return new EvaluateModel[size];
        }
    };

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public long getDrivingTimestamp() {
        return drivingTimestamp;
    }

    public void setDrivingTimestamp(long drivingTimestamp) {
        this.drivingTimestamp = drivingTimestamp;
    }

    public String getDrivingName() {
        return drivingName;
    }

    public void setDrivingName(String drivingName) {
        this.drivingName = drivingName;
    }

    public long getCoachTimestamp1() {
        return coachTimestamp1;
    }

    public void setCoachTimestamp1(long coachTimestamp1) {
        this.coachTimestamp1 = coachTimestamp1;
    }

    public long getCoachTimestamp2() {
        return coachTimestamp2;
    }

    public void setCoachTimestamp2(long coachTimestamp2) {
        this.coachTimestamp2 = coachTimestamp2;
    }

    public String getCoachName1() {
        return coachName1;
    }

    public void setCoachName1(String coachName1) {
        this.coachName1 = coachName1;
    }

    public String getCoachName2() {
        return coachName2;
    }

    public void setCoachName2(String coachName2) {
        this.coachName2 = coachName2;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmd);
        dest.writeLong(drivingTimestamp);
        dest.writeString(drivingName);
        dest.writeLong(coachTimestamp1);
        dest.writeLong(coachTimestamp2);
        dest.writeString(coachName1);
        dest.writeString(coachName2);
        dest.writeByte((byte) (res ? 1 : 0));
        dest.writeString(event);
        dest.writeString(error);
    }
}
