package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ChildGrowthHistoryObject extends GridMatrixDataObject {

    @SerializedName("ChildGrowthId")
    private int childGrowthId;
    @SerializedName("Month")
    private int month;
    @SerializedName("ChildGrowthCategoryId")
    private int childGrowthCategoryId;
    @SerializedName("CategoryName")
    private String categoryName;
    @SerializedName("Status")
    private Boolean active;
    @SerializedName("Text")
    private String text;
    @SerializedName("PatientChildGrowthId")
    private int patientChildGrowthId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;

    public int getChildGrowthId() {
        return childGrowthId;
    }

    public void setChildGrowthId(int childGrowthId) {
        this.childGrowthId = childGrowthId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getChildGrowthCategoryId() {
        return childGrowthCategoryId;
    }

    public void setChildGrowthCategoryId(int childGrowthCategoryId) {
        this.childGrowthCategoryId = childGrowthCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPatientChildGrowthId() {
        return patientChildGrowthId;
    }

    public void setPatientChildGrowthId(int patientChildGrowthId) {
        this.patientChildGrowthId = patientChildGrowthId;
    }

    public int getPatientMenuId() {
        return patientMenuId;
    }

    public void setPatientMenuId(int patientMenuId) {
        this.patientMenuId = patientMenuId;
    }

}
