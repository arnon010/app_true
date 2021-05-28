package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiMenstruationObject extends NormalResponseObject {

    @SerializedName("Data")
    private MenstruationObject data;

    public MenstruationObject getData() {
        return data;
    }

    public void setData(MenstruationObject data) {
        this.data = data;
    }
}