package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListFoodAllergyObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<FoodAllergyObject> dataList;

    public ArrayList<FoodAllergyObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<FoodAllergyObject> dataList) {
        this.dataList = dataList;
    }

}