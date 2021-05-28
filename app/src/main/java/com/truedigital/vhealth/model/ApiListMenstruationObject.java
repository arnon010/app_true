package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListMenstruationObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<MenstruationObject> dataList;

    public ArrayList<MenstruationObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<MenstruationObject> dataList) {
        this.dataList = dataList;
    }

}