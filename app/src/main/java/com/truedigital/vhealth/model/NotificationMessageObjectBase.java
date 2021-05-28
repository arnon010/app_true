package com.truedigital.vhealth.model;

/**
 * Created by nilecon on 4/25/2017 AD.
 */

public class NotificationMessageObjectBase {

    //HEADER
    private String dateTimeTitle;

    //BODY
    private String time;
    private int userId;
    private String message;
    private String createDate;

    public int type;

    public NotificationMessageObjectBase() {
    }

    public NotificationMessageObjectBase(String dateTimeTitle, int type) {
        this.dateTimeTitle = dateTimeTitle;
        this.type = type;
    }

    public NotificationMessageObjectBase(String time, int userId, String message, String createDate, int type) {
        this.time = time;
        this.userId = userId;
        this.message = message;
        this.createDate = createDate;
        this.type = type;
    }

    public String getDateTimeTitle() {
        return dateTimeTitle;
    }

    public void setDateTimeTitle(String dateTimeTitle) {
        this.dateTimeTitle = dateTimeTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
