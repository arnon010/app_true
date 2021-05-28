package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorClinicSubCategoriesDao extends NormalResponseObject {

    @SerializedName("DoctorId") private int doctorId;
    @SerializedName("ClinicSubCategoryId") private int ClinicSubCategoryId;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getClinicSubCategoryId() {
        return ClinicSubCategoryId;
    }

    public void setClinicSubCategoryId(int clinicSubCategoryId) {
        ClinicSubCategoryId = clinicSubCategoryId;
    }
}
