package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiPatientRelationshipObject extends NormalResponseObject{

    @SerializedName("Data")
    private PatientRelationshipObject data;

    public PatientRelationshipObject getRelationship() {
        return data;
    }

    public void setRelationship(PatientRelationshipObject data) {
        this.data = data;
    }

}
