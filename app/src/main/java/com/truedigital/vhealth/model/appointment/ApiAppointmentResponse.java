package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

public class ApiAppointmentResponse extends NormalResponseObject {

    /*
        {
            "AppointmentNumber": "181011000221",
            "AurhorizeUri": null,
            "IsSuccess": true,
            "ErrorMessages": null
        }
    */

    @SerializedName("AppointmentNumber") private String appointmentNumber;
    @SerializedName("AurhorizeUri") private String aurhorizeUri;
    @SerializedName("QrUri") private String qrUri;

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getAurhorizeUri() {
        return aurhorizeUri;
    }

    public void setAurhorizeUri(String aurhorizeUri) {
        this.aurhorizeUri = aurhorizeUri;
    }

    public String getQrUri() {
        return qrUri;
    }

    public void setQrUri(String qrUri) {
        this.qrUri = qrUri;
    }
}
