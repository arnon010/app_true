package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 4/25/2017 AD.
 */

public class ApiNotificationMessageObject {

    @SerializedName("DateTimeNoti")
    private ArrayList<DateTimeNoti> dateTimeNotis;
    @SerializedName("Message")
    private String message;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public ArrayList<DateTimeNoti> getDateTimeNotis() {
        return dateTimeNotis;
    }

    public void setDateTimeNotis(ArrayList<DateTimeNoti> dateTimeNotis) {
        this.dateTimeNotis = dateTimeNotis;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public class DateTimeNoti{
        @SerializedName("DateTimeTitle")
        private String dateTimeTitle;
        @SerializedName("Noti")
        private ArrayList<Noti> notiArrayList;

        public String getDateTimeTitle() {
            return dateTimeTitle;
        }

        public void setDateTimeTitle(String dateTimeTitle) {
            this.dateTimeTitle = dateTimeTitle;
        }

        public ArrayList<Noti> getNotiArrayList() {
            return notiArrayList;
        }

        public void setNotiArrayList(ArrayList<Noti> notiArrayList) {
            this.notiArrayList = notiArrayList;
        }

        public class Noti{
            @SerializedName("HourMin")
            private String time;
            @SerializedName("UserId")
            private int userId;
            @SerializedName("Detail")
            private String message;
            @SerializedName("CreateDate")
            private String createDate;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }
        }
    }
}
