package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BloodPressureObject {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("BloodPressureId")
    private int bloodPressureId;
    @SerializedName("RecordDate")
    private String recordDateString;
    private Date recordDate;
    @SerializedName("SYSValue")
    private int sysValue;
    @SerializedName("DIAValue")
    private int diaValue;
    @SerializedName("DisplayPressure")
    private String displayPressure;
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

    public int getBloodPressureId() {
        return bloodPressureId;
    }

    public void setBloodPressureId(int bloodPressureId) {
        this.bloodPressureId = bloodPressureId;
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

    public int getSYSValue() {
        return sysValue;
    }

    public void setSYSValue(int sysValue) {
        this.sysValue = sysValue;
    }

    public int getDIAValue() {
        return diaValue;
    }

    public void setDIAValue(int diaValue) {
        this.diaValue = diaValue;
    }

    public String getDisplayPressure() {
        return displayPressure;
    }

    public void setDisplayPressure(String displayPressure) {
        this.displayPressure = displayPressure;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

}
