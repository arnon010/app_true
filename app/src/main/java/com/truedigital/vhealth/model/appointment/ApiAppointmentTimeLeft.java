package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

public class ApiAppointmentTimeLeft extends NormalResponseObject {

    @SerializedName("StartCallOn") private String startCallOn;
    @SerializedName("TimeLeft") private String timeLeft;

    public String getStartCallOn() {
        return startCallOn;
    }

    public void setStartCallOn(String startCallOn) {
        this.startCallOn = startCallOn;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }
}
