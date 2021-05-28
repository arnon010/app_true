package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiChildGrowthHistoryObject extends NormalResponseObject {

    @SerializedName("Data")
    private ChildGrowthHistoryObject data;

    public ChildGrowthHistoryObject getData() {
        return data;
    }

    public void setData(ChildGrowthHistoryObject data) {
        this.data = data;
    }
}