package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 2/20/2017 AD.
 */

public class ApiSearchDoctorObject {
    @SerializedName("List")
    private ArrayList<ApiDoctorObject.AccountObject> doctorArrayList;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public ArrayList<ApiDoctorObject.AccountObject> getDoctorArrayList() {
        return doctorArrayList;
    }

    public void setDoctorArrayList(ArrayList<ApiDoctorObject.AccountObject> doctorArrayList) {
        this.doctorArrayList = doctorArrayList;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
