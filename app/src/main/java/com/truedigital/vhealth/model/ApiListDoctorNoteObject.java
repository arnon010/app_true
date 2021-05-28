package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListDoctorNoteObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<DoctorNoteObject> dataList;

    public ArrayList<DoctorNoteObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DoctorNoteObject> dataList) {
        this.dataList = dataList;
    }

}