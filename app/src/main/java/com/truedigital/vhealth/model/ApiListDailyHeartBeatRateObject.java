package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListDailyHeartBeatRateObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<DailyHeartBeatRateObject> dataList;

    public ArrayList<DailyHeartBeatRateObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DailyHeartBeatRateObject> dataList) {
        this.dataList = dataList;
    }

}