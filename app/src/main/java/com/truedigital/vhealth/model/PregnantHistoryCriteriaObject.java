package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PregnantHistoryCriteriaObject {

    private int patientId;
    private int patientMenuId;
    private boolean isCanLoad;
    private int pageIndex;
    private int pageSize;

    @SerializedName("StartDate")
    private String startDateString;
    private Date startDate;
    @SerializedName("EndDate")
    private String endDateString;
    private Date endDate;
    @SerializedName("Place")
    private String place;


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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
