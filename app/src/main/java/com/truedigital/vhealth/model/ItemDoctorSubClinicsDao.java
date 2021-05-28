package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorSubClinicsDao {
    /*
            "ClinicSubCategoryId": 9439,
            "DoctorId": 1,
            "CreatedBy": null,
            "CreatedDate": "2018-09-06T16:41:05",
            "UpdatedBy": null,
            "UpdatedDate": "2018-09-06T16:41:05",
            "CreatedProgram": null,
            "UpdatedProgram": null,
            "ObjectState": 2
    */

    @SerializedName("ClinicSubCategoryId") private int clinicSubCategoryId;
    @SerializedName("DoctorId") private int doctorId;

    public int getClinicSubCategoryId() {
        return clinicSubCategoryId;
    }

    public void setClinicSubCategoryId(int clinicSubCategoryId) {
        this.clinicSubCategoryId = clinicSubCategoryId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
