package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiBloodPressureObject extends NormalResponseObject {

    @SerializedName("Data")
    private BloodPressureObject data;

    public BloodPressureObject getData() {
        return data;
    }

    public void setData(BloodPressureObject data) {
        this.data = data;
    }
}
