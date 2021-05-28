package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MenstruationObject {

    @SerializedName("MenstruationId")
    private int menstruationId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("MenstruationDate")
    private String menstruationDateString;
    private Date menstruationDate;

    public int getMenstruationId() {
        return menstruationId;
    }

    public void setMenstruationId(int menstruationId) {
        this.menstruationId = menstruationId;
    }

    public int getPatientMenuId() {
        return patientMenuId;
    }

    public void setPatientMenuId(int patientMenuId) {
        this.patientMenuId = patientMenuId;
    }

    public Date getMenstruationDate() {
        return menstruationDate;
    }

    public void setMenstruationDate(Date menstruationDate) {
        this.menstruationDate = menstruationDate;
        if (menstruationDate != null) {
            this.menstruationDateString = ConvertDate.convertDateServer(menstruationDate);
        } else {
            this.menstruationDateString = null;
        }
    }

    public String getMenstruationDateString() {
        return menstruationDateString;
    }

    public void setMenstruationDateString(String menstruationDateString) {
        this.menstruationDateString = menstruationDateString;
    }

}
