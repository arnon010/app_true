package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class TodayBloodGlucoseObject {

    @SerializedName("BloodGlucose")
    private String bloodGlucose;

    public String getBloodGlucose() { return bloodGlucose;  }

    public void setBloodGlucose(String bloodGlucose) {  this.bloodGlucose = bloodGlucose; }

}
