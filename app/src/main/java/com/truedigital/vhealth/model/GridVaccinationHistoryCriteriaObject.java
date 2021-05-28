package com.truedigital.vhealth.model;

public class GridVaccinationHistoryCriteriaObject {

    private int patientId;
    private int patientMenuId;
    private Boolean isNecessary;
    private boolean isCanLoad;
    private String menuCode;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientMenuId() {
        return patientMenuId;
    }

    public void setPatientMenuId(int patientMenuId) {
        this.patientMenuId = patientMenuId;
    }

    public Boolean getIsNecessary() { return isNecessary;  }

    public void setIsNecessary(Boolean isNecessary) {
        this.isNecessary = isNecessary;
    }

    public boolean getIsCanLoad() {
        return isCanLoad;
    }

    public void setIsCanLoad(boolean isCanLoad) {
        this.isCanLoad = isCanLoad;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }



//    private int vaccineId;
//
//    public int getVaccineId() { return vaccineId;  }
//
//    public void setVaccineId(int vaccineId) {  this.vaccineId = vaccineId; }


}
