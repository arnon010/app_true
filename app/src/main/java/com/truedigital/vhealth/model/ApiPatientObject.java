package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiPatientObject extends NormalResponseObject {

    @SerializedName("token")
    private String token;

    @SerializedName("Data")
    private PatientObject data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PatientObject getData() {
        return data;
    }

    public void setData(PatientObject data) {
        this.data = data;
    }

}
