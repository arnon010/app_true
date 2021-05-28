package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

public class ApiAppointmentCancel extends NormalResponseObject {

    /*
    {
        "Reason": "หายเป็นปรกติแล้ว",
            "IsPatient": true,
            "IsBefore24Hour": true
    }
    */

    @SerializedName("Reason") private String reason;
    @SerializedName("IsPatient") private boolean patient;
    @SerializedName("IsBefore24Hour") private boolean before24Hour;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isPatient() {
        return patient;
    }

    public void setPatient(boolean patient) {
        this.patient = patient;
    }

    public boolean isBefore24Hour() {
        return before24Hour;
    }

    public void setBefore24Hour(boolean before24Hour) {
        this.before24Hour = before24Hour;
    }
}
