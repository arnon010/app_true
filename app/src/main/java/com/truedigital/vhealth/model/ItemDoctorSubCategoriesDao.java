package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorSubCategoriesDao extends NormalResponseObject {

    @SerializedName("DoctorId") private int doctorId;
    @SerializedName("SubCategoryId") private int SubCategoryId;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        SubCategoryId = subCategoryId;
    }
}
