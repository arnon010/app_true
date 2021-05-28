package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DailyBloodSugarObject {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("DisplayBloodGlucose")
    private String displayBloodGlucose;
    @SerializedName("DailyDate")
    private String dailyDateString;
    private Date dailyDate;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientMenuId() {
        return patientMenuId;
    }

    public void setPatientMenuId(int patientMenuId) {
        this.patientMenuId = patientMenuId;
    }

    public String getDisplayBloodGlucose() {
        return displayBloodGlucose;
    }

    public void setDisplayBloodGlucose(String displayBloodGlucose) {
        this.displayBloodGlucose = displayBloodGlucose;
    }

    public Date getDailyDate() {
        return dailyDate;
    }

    public void setDailyDate(Date dailyDate) {
        this.dailyDate = dailyDate;
        if(dailyDate != null)
        {
            this.dailyDateString = ConvertDate.convertDateServer(dailyDate);
        }
        else
        {
            this.dailyDateString = null;
        }
    }

    public String getDailyDateString() {
        return dailyDateString;
    }

    public void setDailyDateString(String dailyDateString) {
        this.dailyDateString = dailyDateString;
    }
}
