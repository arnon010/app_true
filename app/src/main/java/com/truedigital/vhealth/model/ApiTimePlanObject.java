package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nilecon on 3/6/2017 AD.
 */

public class ApiTimePlanObject implements Serializable {
    @SerializedName("MasterTimePlan")
    private ArrayList<MasterTimePlan> masterTimePlanArrayList;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

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

    public ArrayList<MasterTimePlan> getMasterTimePlanArrayList() {
        return masterTimePlanArrayList;
    }

    public void setMasterTimePlanArrayList(ArrayList<MasterTimePlan> masterTimePlanArrayList) {
        this.masterTimePlanArrayList = masterTimePlanArrayList;
    }

    public class MasterTimePlan implements Serializable {
        @SerializedName("DoctorMasterReadyTimeId")
        private int doctorMasterReadyTimeId;
        @SerializedName("AccDoctorId")
        private int accDoctorId;
        @SerializedName("Days")
        private String days;
        @SerializedName("MasterReadyTime")
        private String masterReadyTime;
        @SerializedName("MasterReadyPeriod")
        private String masterReadyPeriod;
        @SerializedName("IsHaveTimeReady")
        private boolean isHaveTimeReady;
        @SerializedName("isSingleDay")
        private boolean isSingleDay;
        @SerializedName("Scheduleddate")
        private String Scheduleddate;
        @SerializedName("TypeList")
        private ArrayList<TypesList> typesLists;

        public boolean isSingleDay() {
            return isSingleDay;
        }

        public void setSingleDay(boolean singleDay) {
            isSingleDay = singleDay;
        }

        public String getScheduleddate() {
            return Scheduleddate;
        }

        public void setScheduleddate(String scheduleddate) {
            Scheduleddate = scheduleddate;
        }

        public boolean isHaveTimeReady() {
            return isHaveTimeReady;
        }

        public void setHaveTimeReady(boolean haveTimeReady) {
            isHaveTimeReady = haveTimeReady;
        }

        public int getDoctorMasterReadyTimeId() {
            return doctorMasterReadyTimeId;
        }

        public void setDoctorMasterReadyTimeId(int doctorMasterReadyTimeId) {
            this.doctorMasterReadyTimeId = doctorMasterReadyTimeId;
        }

        public int getAccDoctorId() {
            return accDoctorId;
        }

        public void setAccDoctorId(int accDoctorId) {
            this.accDoctorId = accDoctorId;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getMasterReadyTime() {
            return masterReadyTime;
        }

        public void setMasterReadyTime(String masterReadyTime) {
            this.masterReadyTime = masterReadyTime;
        }

        public String getMasterReadyPeriod() {
            return masterReadyPeriod;
        }

        public void setMasterReadyPeriod(String masterReadyPeriod) {
            this.masterReadyPeriod = masterReadyPeriod;
        }

        public ArrayList<TypesList> getTypesLists() {
            return typesLists;
        }

        public void setTypesLists(ArrayList<TypesList> typesLists) {
            this.typesLists = typesLists;
        }

        public class TypesList implements Serializable{

            @SerializedName("TypeTitle")
            private String typeTitle;
            @SerializedName("Id")
            private int id;
            @SerializedName("MasterReadyTimeId")
            private int masterReadyTimeId;
            @SerializedName("TypeId")
            private int typeId;
            @SerializedName("Price")
            private int price;

            public String getTypeTitle() {
                return typeTitle;
            }

            public void setTypeTitle(String typeTitle) {
                this.typeTitle = typeTitle;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMasterReadyTimeId() {
                return masterReadyTimeId;
            }

            public void setMasterReadyTimeId(int masterReadyTimeId) {
                this.masterReadyTimeId = masterReadyTimeId;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}
