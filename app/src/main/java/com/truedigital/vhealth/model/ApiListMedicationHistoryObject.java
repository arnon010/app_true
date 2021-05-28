package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListMedicationHistoryObject  extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<MedicationHistoryObject> dataList;

    public ArrayList<MedicationHistoryObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<MedicationHistoryObject> dataList) {
        this.dataList = dataList;
    }

}