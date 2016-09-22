package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 16/1/5.
 * 资讯model
 */
public class ConsultBean {
    private String id;
    private String type;
    private String content;
    private String answer;
    private boolean isHandled;
    private String consultTime;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isHandled() {
        return isHandled;
    }

    public void setIsHandled(boolean isHandled) {
        this.isHandled = isHandled;
    }

    public String getConsultTime() {
        return consultTime;
    }

    public void setConsultTime(String consultTime) {
        this.consultTime = consultTime;
    }

    @Override
    public String toString() {
        return "ConsultBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                ", isHandled=" + isHandled +
                ", consultTime='" + consultTime + '\'' +
                '}';
    }
}
