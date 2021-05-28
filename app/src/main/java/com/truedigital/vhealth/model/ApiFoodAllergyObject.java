package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiFoodAllergyObject extends NormalResponseObject {

    @SerializedName("Data")
    private FoodAllergyObject data;

    public FoodAllergyObject getData() {
        return data;
    }

    public void setData(FoodAllergyObject data) {
        this.data = data;
    }
}