package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiMedicineAllergyObject extends NormalResponseObject {

    @SerializedName("Data")
    private MedicineAllergyObject data;

    public MedicineAllergyObject getData() {
        return data;
    }

    public void setData(MedicineAllergyObject data) {
        this.data = data;
    }
}