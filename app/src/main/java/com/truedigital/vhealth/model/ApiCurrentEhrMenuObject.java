package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiCurrentEhrMenuObject extends NormalResponseObject {

    @SerializedName("Data")
    private CurrentEhrMenuObject data;

    public CurrentEhrMenuObject getData() {
        return data;
    }

    public void setData(CurrentEhrMenuObject data) {
        this.data = data;
    }
}