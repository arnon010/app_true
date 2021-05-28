package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class PatientRelationshipObject {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("RelationshipPatientId")
    private int relationshipPatientId;
    @SerializedName("RelationshipId")
    private int relationshipId;
    @SerializedName("RelationshipName")
    private String relationshipName;
    @SerializedName("MenuCode")
    private String menuCode;

    public int getPatientId() { return patientId;  }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientMenuId() { return patientMenuId;  }

    public void setPatientMenuId(int patientMenuId) {
        this.patientMenuId = patientMenuId;
    }

    public int getRelationshipPatientId() { return relationshipPatientId;  }

    public void setRelationshipPatientId(int relationshipPatientId) {
        this.relationshipPatientId = relationshipPatientId;
    }

    public int getRelationshipId() { return relationshipId;  }

    public void setRelationshipId(int relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getRelationshipName() { return relationshipName;  }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public String getMenuCode() { return menuCode;  }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

}
