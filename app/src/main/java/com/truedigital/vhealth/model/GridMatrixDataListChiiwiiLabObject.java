package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GridMatrixDataListChiiwiiLabObject extends GridMatrixDataListObject<LaboratoryChiiwiiObject> {

    @SerializedName("PatientChiiwiiLabList")
    private ArrayList<LaboratoryChiiwiiObject> dataItemList;

    public ArrayList<LaboratoryChiiwiiObject> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(ArrayList<LaboratoryChiiwiiObject> dataItemList) {
        this.dataItemList = dataItemList;
    }

}