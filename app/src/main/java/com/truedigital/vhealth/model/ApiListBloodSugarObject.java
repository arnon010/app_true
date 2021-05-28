package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListBloodSugarObject  extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<BloodSugarObject> dataList;

    public ArrayList<BloodSugarObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<BloodSugarObject> dataList) {
        this.dataList = dataList;
    }

}