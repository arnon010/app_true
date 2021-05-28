package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 3/10/2017 AD.
 */

public class ApiTimePlanByDateObject {

    @SerializedName("TypeList")
    private ArrayList<ApiTimePlanObject.MasterTimePlan.TypesList> typesLists;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public ArrayList<ApiTimePlanObject.MasterTimePlan.TypesList> getTypesLists() {
        return typesLists;
    }

    public void setTypesLists(ArrayList<ApiTimePlanObject.MasterTimePlan.TypesList> typesLists) {
        this.typesLists = typesLists;
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
