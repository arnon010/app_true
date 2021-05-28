package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiChartObject extends NormalResponseObject {

    @SerializedName("Data")
    private ChartObject data;

    public ChartObject getData() {
        return data;
    }

    public void setData(ChartObject data) {
        this.data = data;
    }
}