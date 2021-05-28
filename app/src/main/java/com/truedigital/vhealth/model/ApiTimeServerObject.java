package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nilecon on 4/11/2017 AD.
 */

public class ApiTimeServerObject {

    @SerializedName("ServerDateTime")
    private String timeServer;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public String getTimeServer() {
        return timeServer;
    }

    public void setTimeServer(String timeServer) {
        this.timeServer = timeServer;
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
