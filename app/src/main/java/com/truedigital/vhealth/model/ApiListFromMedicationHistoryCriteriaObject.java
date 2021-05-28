package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListFromMedicationHistoryCriteriaObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<String> criteriaList;

    public ArrayList<String> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(ArrayList<String> criteriaList) {
        this.criteriaList = criteriaList;
    }

}