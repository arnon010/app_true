package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songkrit on 8/24/2017 AD.
 */

public class ApiCurrentAppointment extends NormalResponseObject {
    @SerializedName("UserId") private int userId;
    @SerializedName("AppointmentNumber") private String appointmentNumber;
    @SerializedName("ContactTypeId") private int contactTypeId;
    @SerializedName("UserTypeId") private int userTypeId;
    @SerializedName("Message") private String message;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public int getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(int contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
