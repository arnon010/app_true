package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiGridVaccinationHistoryObject extends NormalResponseObject {

    @SerializedName("Data")
    private GridVaccinationHistoryObject data;

    public GridVaccinationHistoryObject getData() {
        return data;
    }

    public void setData(GridVaccinationHistoryObject data) {
        this.data = data;
    }
}
