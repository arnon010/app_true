package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class TodayBloodPressureObject {

    @SerializedName("SYS")
    private String Sys;
    @SerializedName("DIA")
    private String Dia;


    public String getSys() { return Sys;  }

    public void setSys(String sys) {  this.Sys = sys; }

    public String getDia() { return Dia;  }

    public void setDia(String dia) {
        this.Dia = dia;
    }


}
