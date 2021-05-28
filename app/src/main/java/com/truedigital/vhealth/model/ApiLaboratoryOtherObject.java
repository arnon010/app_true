package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiLaboratoryOtherObject extends NormalResponseObject {

    @SerializedName("Data")
    private LaboratoryOtherObject data;

    public LaboratoryOtherObject getData() {
        return data;
    }

    public void setData(LaboratoryOtherObject data) {
        this.data = data;
    }
}