package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiMedicationHistoryObject extends NormalResponseObject {

    @SerializedName("Data")
    private MedicationHistoryObject data;

    public MedicationHistoryObject getData() {
        return data;
    }

    public void setData(MedicationHistoryObject data) {
        this.data = data;
    }
}