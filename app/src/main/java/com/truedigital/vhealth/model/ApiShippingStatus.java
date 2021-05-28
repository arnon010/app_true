package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songkrit on 2/6/2017 AD.
 */

public class ApiShippingStatus extends NormalResponseObject {

    @SerializedName("AppointmentNumber") private String AppointmentNumber;
    @SerializedName("Status") private String Status;

    public String getAppointmentNumber() {
        return AppointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        AppointmentNumber = appointmentNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
