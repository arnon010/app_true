package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nilecon on 2/17/2017 AD.
 */

public class ApiFilterObject {

    @SerializedName("List")
    private ArrayList<FilterList> filterArrayList;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public ArrayList<FilterList> getFilterArrayList() {
        return filterArrayList;
    }

    public void setFilterArrayList(ArrayList<FilterList> filterArrayList) {
        this.filterArrayList = filterArrayList;
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

    public class FilterList implements Serializable {

        @SerializedName("Id")
        private int id;
        @SerializedName("Title")
        private String title;
        @SerializedName("Images")
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
