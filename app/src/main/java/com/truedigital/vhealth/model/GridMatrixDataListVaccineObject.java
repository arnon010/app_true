package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GridMatrixDataListVaccineObject extends GridMatrixDataListObject<VaccinationHistoryObject> {

    @SerializedName("PatientVaccinationList")
    private ArrayList<VaccinationHistoryObject> dataItemList;

    public ArrayList<VaccinationHistoryObject> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(ArrayList<VaccinationHistoryObject> dataItemList) {
        this.dataItemList = dataItemList;
    }

}
