package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DoctorNoteCriteriaObject {

    private int patientId;
    private boolean isCanLoad;
    private int pageIndex;
    private int pageSize;

    @SerializedName("StartDate")
    private String startDateString;
    private Date startDate;
    @SerializedName("EndDate")
    private String endDateString;
    private Date endDate;
    @SerializedName("DoctorId")
    private int doctorId;
    @SerializedName("DoctorName")
    private String doctorName;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public boolean getIsCanLoad() {
        return isCanLoad;
    }

    public void setIsCanLoad(boolean isCanLoad) {
        this.isCanLoad = isCanLoad;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        if(startDate != null)
        {
            this.startDateString = ConvertDate.convertDateServer(startDate);
        }
        else
        {
            this.startDateString = null;
        }
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        if(endDate != null)
        {
            this.endDateString = ConvertDate.convertDateServer(endDate);
        }
        else
        {
            this.endDateString = null;
        }
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorIdDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }



}
