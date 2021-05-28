package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiTodayHeartRestingRateObject extends NormalResponseObject {

    @SerializedName("Data")
    private TodayHeartRestingRateObject data;

    public TodayHeartRestingRateObject getData() {
        return data;
    }

    public void setData(TodayHeartRestingRateObject data) {
        this.data = data;
    }
}