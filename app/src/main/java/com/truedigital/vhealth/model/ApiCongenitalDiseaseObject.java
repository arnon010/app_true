package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiCongenitalDiseaseObject extends NormalResponseObject {

    @SerializedName("Data")
    private CongenitalDiseaseObject data;

    public CongenitalDiseaseObject getData() {
        return data;
    }

    public void setData(CongenitalDiseaseObject data) {
        this.data = data;
    }
}