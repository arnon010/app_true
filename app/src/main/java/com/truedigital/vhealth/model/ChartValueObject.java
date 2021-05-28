package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ChartValueObject {

    @SerializedName("ChartValue")
    private int chartValue;
    @SerializedName("Description")
    private String description;

    public int getChartValue() {
        return chartValue;
    }

    public void setChartValue(int chartValue) {
        this.chartValue = chartValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
