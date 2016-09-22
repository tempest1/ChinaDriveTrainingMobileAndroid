package com.smartlab.drivetrain.model;

import java.io.Serializable;

/**
 * Created by smartlab on 15/9/17.
 * 教练信息简介 model
 */
public class CoachBrief extends Entity implements Serializable {

    private String coachId;//教练员唯一标识信息
private String coachName;//教练员姓名
private String coachTimestamp;//教练时间戳
private String belongTo;//教练员编制，即所属驾校
private Boolean IsPrivate;//是否是私人教练
private float enrollPrice;//报名价格
private String phone;//联系电话
private double latitude;
private double longitude;
private String coachPic;

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCoachTimestamp()
    {return coachTimestamp;}
    public void setCoachTimestamp(String coachTimestamp)
    {
        this.coachTimestamp=coachTimestamp;
    }
    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public Boolean getIsPrivate() {
        return IsPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        IsPrivate = isPrivate;
    }

    public float getEnrollPrice() {
        return enrollPrice;
    }

    public void setEnrollPrice(float enrollPrice) {
        this.enrollPrice = enrollPrice;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCoachPic() {
        return coachPic;
    }

    public void setCoachPic(String coachPic) {
        this.coachPic = coachPic;
    }

    @Override
    public String toString() {
        return "CoachBrief{" +
                "coachId='" + coachId + '\'' +
                ", coachName='" + coachName + '\'' +
                ", belongTo='" + belongTo + '\'' +
                ", IsPrivate=" + IsPrivate +
                ", enrollPrice=" + enrollPrice +
                ", phone='" + phone + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", coachPic='" + coachPic + '\'' +
                '}';
    }
}

