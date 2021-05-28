package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class HeartBeatRateObject {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("HeartRateId")
    private int heartRateId;
    @SerializedName("RecordDate")
    private String recordDateString;
    private Date recordDate;
    @SerializedName("Type")
    private String type;
    @SerializedName("BPM")
    private int bpm;
    @SerializedName("DisplayBPM")
    private String displayBPM;
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

    public int getHeartRateId() {
        return heartRateId;
    }

    public void setHeartRateId(int heartRateId) {
        this.heartRateId = heartRateId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBPM() {
        return bpm;
    }

    public void setBPM(int bpm) {
        this.bpm = bpm;
    }

    public String getDisplayBPM() {
        return displayBPM;
    }

    public void setDisplayBPM(String displayBPM) {
        this.displayBPM = displayBPM;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }


}
