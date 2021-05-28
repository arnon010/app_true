package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListHeartBeatRateTypeObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<HeartBeatRateTypeObject> dataList;

    public ArrayList<HeartBeatRateTypeObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<HeartBeatRateTypeObject> dataList) {
        this.dataList = dataList;
    }

}