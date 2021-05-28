package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class RowHeaderVaccineObject extends GridMatrixRowHeaderObject {

    @SerializedName("VaccineId")
    private int vaccineId;
    @SerializedName("IsNecessary")
    private boolean isNecessary;
    @SerializedName("Name")
    private String name;
    @SerializedName("Detail")
    private String detail;

    public int getVaccineId() { return vaccineId;  }

    public void setVaccineId(int vaccineId) {  this.vaccineId = vaccineId; }

    public boolean getIsNecessary() { return isNecessary;  }

    public void setIsNecessary(boolean isNecessary) {  this.isNecessary = isNecessary; }

    public String getName() { return name;  }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() { return detail;  }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
