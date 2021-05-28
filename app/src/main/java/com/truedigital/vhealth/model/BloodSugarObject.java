package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BloodSugarObject {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("BloodSugarId")
    private int bloodSugarId;
    @SerializedName("RecordDate")
    private String recordDateString;
    private Date recordDate;
    @SerializedName("SugarValue")
    private int sugarValue;
    @SerializedName("DisplayBloodGlucose")
    private String displayBloodGlucose;
    private String menuCode;

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

    public int getBloodSugarId() {
        return bloodSugarId;
    }

    public void setBloodSugarId(int bloodSugarId) {
        this.bloodSugarId = bloodSugarId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
        if (recordDate != null) {
            this.recordDateString = ConvertDate.convertDateToServiceFormat(recordDate);
        } else {
            this.recordDateString = null;
        }
    }

    public String getRecordDateString() {
        return recordDateString;
    }

    public void setRecordDateString(String recordDateString) {
        this.recordDateString = recordDateString;
    }

    public int getSugarValue() {
        return sugarValue;
    }

    public void setSugarValue(int sugarValue) {
        this.sugarValue = sugarValue;
    }

    public String getDisplayBloodGlucose() {
        return displayBloodGlucose;
    }

    public void setDisplayBloodGlucose(String displayBloodGlucose) {
        this.displayBloodGlucose = displayBloodGlucose;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

}
