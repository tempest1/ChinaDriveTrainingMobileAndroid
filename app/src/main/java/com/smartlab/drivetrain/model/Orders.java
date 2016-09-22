package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/12/24.
 * 订单model
 */
public class Orders extends Entity implements Parcelable {
    private String orderId;
    private double money;
    private String productDescription;
    private String description;
    private String time;
    private String state;
    private String paymentType;
    private String productTimestamp;
    private String name;

    public Orders(){}

    protected Orders(Parcel in) {
        orderId = in.readString();
        money = in.readDouble();
        productDescription = in.readString();
        description = in.readString();
        time = in.readString();
        state = in.readString();
        paymentType = in.readString();
        productTimestamp = in.readString();
        name = in.readString();
    }

    public static final Creator<Orders> CREATOR = new Creator<Orders>() {
        @Override
        public Orders createFromParcel(Parcel in) {
            return new Orders(in);
        }

        @Override
        public Orders[] newArray(int size) {
            return new Orders[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getProductTimestamp() {
        return productTimestamp;
    }

    public void setProductTimestamp(String productTimestamp) {
        this.productTimestamp = productTimestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", money=" + money +
                ", productDescription='" + productDescription + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", productTimestamp='" + productTimestamp + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeDouble(money);
        dest.writeString(productDescription);
        dest.writeString(description);
        dest.writeString(time);
        dest.writeString(state);
        dest.writeString(paymentType);
        dest.writeString(productTimestamp);
        dest.writeString(name);
    }
}
