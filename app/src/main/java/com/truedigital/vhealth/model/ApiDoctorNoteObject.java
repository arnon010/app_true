package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiDoctorNoteObject extends NormalResponseObject {

    @SerializedName("Data")
    private DoctorNoteObject data;

    public DoctorNoteObject getData() {
        return data;
    }

    public void setData(DoctorNoteObject data) {
        this.data = data;
    }
}