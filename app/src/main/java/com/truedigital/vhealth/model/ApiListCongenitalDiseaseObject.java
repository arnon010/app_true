package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListCongenitalDiseaseObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<CongenitalDiseaseObject> dataList;

    public ArrayList<CongenitalDiseaseObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<CongenitalDiseaseObject> dataList) {
        this.dataList = dataList;
    }

}