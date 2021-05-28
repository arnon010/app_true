package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiGridChildGrowthHistoryObject extends NormalResponseObject {

    @SerializedName("Data")
    private GridChildGrowthHistoryObject data;

    public GridChildGrowthHistoryObject getData() {
        return data;
    }

    public void setData(GridChildGrowthHistoryObject data) {
        this.data = data;
    }
}
