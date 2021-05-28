package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiTodayBloodGlucoseObject extends NormalResponseObject {

    @SerializedName("Data")
    private TodayBloodGlucoseObject data;

    public TodayBloodGlucoseObject getData() {
        return data;
    }

    public void setData(TodayBloodGlucoseObject data) {
        this.data = data;
    }
}