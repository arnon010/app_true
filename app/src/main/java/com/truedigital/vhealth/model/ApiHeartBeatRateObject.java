package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiHeartBeatRateObject extends NormalResponseObject {

    @SerializedName("Data")
    private HeartBeatRateObject data;

    public HeartBeatRateObject getData() {
        return data;
    }

    public void setData(HeartBeatRateObject data) {
        this.data = data;
    }
}
