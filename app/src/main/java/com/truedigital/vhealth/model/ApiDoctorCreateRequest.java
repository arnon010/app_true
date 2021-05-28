package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;


public class ApiDoctorCreateRequest extends NormalResponseObject {

    @SerializedName("Doctor") private ApiDoctorRequest Doctor;

    public ApiDoctorRequest getDoctor() {
        return Doctor;
    }

    public void setDoctor(ApiDoctorRequest doctor) {
        Doctor = doctor;
    }
}
