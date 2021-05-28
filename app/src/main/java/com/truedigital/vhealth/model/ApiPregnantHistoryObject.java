package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;


public class ApiPregnantHistoryObject  extends NormalResponseObject {

    @SerializedName("Data")
    private PregnantHistoryObject data;

    public PregnantHistoryObject getData() {
        return data;
    }

    public void setData(PregnantHistoryObject data) {
        this.data = data;
    }
}