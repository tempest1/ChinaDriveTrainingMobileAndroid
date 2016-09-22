package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 15/12/26.
 * 投诉类型
 */
public class ComplainType {
    private String type;
    private String typeCn;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeCn() {
        return typeCn;
    }

    public void setTypeCn(String typeCn) {
        this.typeCn = typeCn;
    }

    @Override
    public String toString() {
        return "ComplainType{" +
                "type='" + type + '\'' +
                ", typeCn='" + typeCn + '\'' +
                '}';
    }
}
