package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListDailyBloodSugarObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<DailyBloodSugarObject> dataList;

    public ArrayList<DailyBloodSugarObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DailyBloodSugarObject> dataList) {
        this.dataList = dataList;
    }

}