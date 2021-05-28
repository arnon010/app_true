package com.truedigital.vhealth.model;

public class FoodAllergyCriteriaObject {

    private int patientId;
    private int patientMenuId;
    private int pageIndex;
    private int pageSize;

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

}
