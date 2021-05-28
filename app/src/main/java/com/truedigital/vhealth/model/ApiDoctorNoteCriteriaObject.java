package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;


public class ApiDoctorNoteCriteriaObject extends NormalResponseObject {

    @SerializedName("Data")
    private DoctorNoteCriteriaObject criteria;

    public DoctorNoteCriteriaObject getCriteria() {
        return criteria;
    }

    public void setCriteria(DoctorNoteCriteriaObject criteria) {
        this.criteria = criteria;
    }
}
