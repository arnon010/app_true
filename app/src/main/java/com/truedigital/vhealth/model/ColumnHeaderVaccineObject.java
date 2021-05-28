package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;


public class ColumnHeaderVaccineObject {

    @SerializedName("Month")
    private int month;
    @SerializedName("MonthDescription")
    private String monthDescription;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getMonthDescription() {
        return monthDescription;
    }

    public void setMonthDescription(String monthDescription) {
        this.monthDescription = monthDescription;
    }



}
