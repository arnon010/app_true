package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiVaccinationHistoryObject  extends NormalResponseObject {

    @SerializedName("Data")
    private VaccinationHistoryObject data;

    public VaccinationHistoryObject getData() {
        return data;
    }

    public void setData(VaccinationHistoryObject data) {
        this.data = data;
    }
}