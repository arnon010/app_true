package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListPatientRelationshipObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<PatientRelationshipObject> relationshipList;

    public ArrayList<PatientRelationshipObject> getRelationshipList() {
        return relationshipList;
    }

    public void setRelationshipList(ArrayList<PatientRelationshipObject> menrelationshipListuList) {
        this.relationshipList = relationshipList;
    }

}