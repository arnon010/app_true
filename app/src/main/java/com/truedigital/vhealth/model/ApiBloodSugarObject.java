package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiBloodSugarObject extends NormalResponseObject {

    @SerializedName("Data")
    private BloodSugarObject data;

    public BloodSugarObject getData() {
        return data;
    }

    public void setData(BloodSugarObject data) {
        this.data = data;
    }
}
