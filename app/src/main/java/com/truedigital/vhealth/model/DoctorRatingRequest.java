package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;


public class DoctorRatingRequest extends NormalResponseObject {
    /*
    {
        "Rate": 5,
            "Comment": "Too good to be true",
            "AppointmentNumber": "180010101001"
    }
    */

    @SerializedName("Rate") private int rate;
    @SerializedName("Comment") private String comment;
    @SerializedName("AppointmentNumber") private String appointmentNumber;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }
}
