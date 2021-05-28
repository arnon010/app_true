package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GridMatrixDataListChildGrowthObject extends GridMatrixDataListObject<ChildGrowthHistoryObject> {

    @SerializedName("PatientChildGrowthList")
    private ArrayList<ChildGrowthHistoryObject> dataItemList;

    public ArrayList<ChildGrowthHistoryObject> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(ArrayList<ChildGrowthHistoryObject> dataItemList) {
        this.dataItemList = dataItemList;
    }

}
