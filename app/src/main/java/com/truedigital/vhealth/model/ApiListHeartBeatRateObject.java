package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListHeartBeatRateObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<HeartBeatRateObject> dataList;

    public ArrayList<HeartBeatRateObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<HeartBeatRateObject> dataList) {
        this.dataList = dataList;
    }

}