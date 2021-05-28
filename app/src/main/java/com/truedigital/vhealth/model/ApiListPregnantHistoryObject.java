package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListPregnantHistoryObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<PregnantHistoryObject> dataList;

    public ArrayList<PregnantHistoryObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<PregnantHistoryObject> dataList) {
        this.dataList = dataList;
    }

}