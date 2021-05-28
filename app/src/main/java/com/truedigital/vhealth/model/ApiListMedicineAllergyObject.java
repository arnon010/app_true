package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListMedicineAllergyObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<MedicineAllergyObject> dataList;

    public ArrayList<MedicineAllergyObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<MedicineAllergyObject> dataList) {
        this.dataList = dataList;
    }

}