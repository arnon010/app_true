package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListDoctorNoteCriteriaObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<DoctorNoteCriteriaObject> criteriaList;

    public ArrayList<DoctorNoteCriteriaObject> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(ArrayList<DoctorNoteCriteriaObject> criteriaList) {
        this.criteriaList = criteriaList;
    }

}
