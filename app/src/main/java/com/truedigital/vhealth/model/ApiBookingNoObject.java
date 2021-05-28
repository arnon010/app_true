package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nilecon on 3/13/2017 AD.
 */

public class ApiBookingNoObject implements Serializable{

    @SerializedName("AppointmentNumber")
    private String appointmentNo;
    @SerializedName("TotalPrice")
    private int totalPrice;
    @SerializedName("PriceList")
    private ArrayList<PriceList> priceArrayList;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public String getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(String appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<PriceList> getPriceArrayList() {
        return priceArrayList;
    }

    public void setPriceArrayList(ArrayList<PriceList> priceArrayList) {
        this.priceArrayList = priceArrayList;
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

    public class PriceList implements Serializable {
        @SerializedName("Detail")
        private String detail;
        @SerializedName("Price")
        private int price;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
