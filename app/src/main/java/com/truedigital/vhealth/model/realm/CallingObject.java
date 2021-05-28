package com.truedigital.vhealth.model.realm;

import io.realm.RealmObject;

/**
 * Created by nilecon on 4/19/2017 AD.
 */

public class CallingObject extends RealmObject{

    private String appointmentNumber;
    private int userId;
    private int userType;
    private int contactType;
    private boolean isInTime;
    private String message;

    public CallingObject() {
    }

    public CallingObject(String appointmentNumber, int userId, int userType, int contactType) {
        this.appointmentNumber = appointmentNumber;
        this.userId = userId;
        this.userType = userType;
        this.contactType = contactType;
        //this.isInTime = isInTime;
        //this.message = message;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getContactType() {
        return contactType;
    }

    public void setContactType(int contactType) {
        this.contactType = contactType;
    }

    public boolean isInTime() {
        return isInTime;
    }

    public void setInTime(boolean inTime) {
        isInTime = inTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
