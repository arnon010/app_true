package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiTodayBloodPressureObject extends NormalResponseObject {

    @SerializedName("Data")
    private TodayBloodPressureObject data;

    public TodayBloodPressureObject getData() {
        return data;
    }

    public void setData(TodayBloodPressureObject data) {
        this.data = data;
    }
}