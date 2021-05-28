package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class TodayHeartRestingRateObject {

    @SerializedName("HeartRate")
    private String heartRate;
    @SerializedName("RestingRate")
    private String restingRate;
   

    public String getHeartRate() { return heartRate;  }

    public void setHeartRate(String heartRate) {  this.heartRate = heartRate; }

    public String getRestingRate() { return restingRate;  }

    public void setRestingRate(String restingRate) {
        this.restingRate = restingRate;
    }


}
