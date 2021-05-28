package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListBloodPressureObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<BloodPressureObject> dataList;

    public ArrayList<BloodPressureObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<BloodPressureObject> dataList) {
        this.dataList = dataList;
    }

}