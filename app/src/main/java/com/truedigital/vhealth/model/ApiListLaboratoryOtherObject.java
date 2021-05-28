package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListLaboratoryOtherObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<LaboratoryOtherObject> dataList;

    public ArrayList<LaboratoryOtherObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<LaboratoryOtherObject> dataList) {
        this.dataList = dataList;
    }

}