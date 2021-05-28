package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListDailyBloodPressureObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<DailyBloodPressureObject> dataList;

    public ArrayList<DailyBloodPressureObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DailyBloodPressureObject> dataList) {
        this.dataList = dataList;
    }

}