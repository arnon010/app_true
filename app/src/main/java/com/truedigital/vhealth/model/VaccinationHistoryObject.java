package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class VaccinationHistoryObject extends GridMatrixDataObject {

    @SerializedName("VaccineId")
    private int vaccineId;
    @SerializedName("IsNecessary")
    private Boolean isNecessary;
    @SerializedName("Month")
    private int month;
    @SerializedName("Status")
    private Boolean active;
    @SerializedName("PatientVaccinationId")
    private int patientVaccinationId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;

    public int getVaccineId() { return vaccineId;  }

    public void setVaccineId(int vaccineId) {  this.vaccineId = vaccineId; }

    public Boolean getIsNecessary() { return isNecessary;  }

    public void setIsNecessary(Boolean isNecessary) {
        this.isNecessary = isNecessary;
    }

    public int getMonth() { return month;  }

    public void setMonth(int month) {  this.month = month; }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getPatientVaccinationId() { return patientVaccinationId;  }

    public void setPatientVaccinationId(int patientVaccinationId) {  this.patientVaccinationId = patientVaccinationId; }

    public int getPatientMenuId() { return patientMenuId;  }

    public void setPatientMenuId(int patientMenuId) {  this.patientMenuId = patientMenuId; }

}
