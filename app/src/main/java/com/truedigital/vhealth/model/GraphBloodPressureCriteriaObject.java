package com.truedigital.vhealth.model;

public class GraphBloodPressureCriteriaObject {

    private int patientId;
    private int patientMenuId;
    private boolean isCanLoad;

    private int type;
    private int period;

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

    public boolean getIsCanLoad() {
        return isCanLoad;
    }

    public void setIsCanLoad(boolean isCanLoad) {
        this.isCanLoad = isCanLoad;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

}
