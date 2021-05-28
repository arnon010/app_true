package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 3/9/2017 AD.
 */

public class ApiMonthReadyTimeObject {

    @SerializedName("DayOfmonth")
    private ArrayList<DayOfMonth> dayOfMonthArrayList;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public ArrayList<DayOfMonth> getDayOfMonthArrayList() {
        return dayOfMonthArrayList;
    }

    public void setDayOfMonthArrayList(ArrayList<DayOfMonth> dayOfMonthArrayList) {
        this.dayOfMonthArrayList = dayOfMonthArrayList;
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

    public class DayOfMonth{
        @SerializedName("DateTime")
        private String date;
        @SerializedName("IsHaveTimeReady")
        private boolean isHaveTimeReady;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public boolean isHaveTimeReady() {
            return isHaveTimeReady;
        }

        public void setHaveTimeReady(boolean haveTimeReady) {
            isHaveTimeReady = haveTimeReady;
        }
    }
}
