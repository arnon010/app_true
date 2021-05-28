package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 2/27/2017 AD.
 */

public class ApiFavoriteObject {

    @SerializedName("List")
    private ArrayList<ApiDoctorObject.AccountObject> accountArrayList;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public ArrayList<ApiDoctorObject.AccountObject> getAccountArrayList() {
        return accountArrayList;
    }

    public void setAccountArrayList(ArrayList<ApiDoctorObject.AccountObject> accountArrayList) {
        this.accountArrayList = accountArrayList;
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
