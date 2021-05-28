package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 3/8/2017 AD.
 */

public class ApiScheduleAppointmentObject { // api time plan

    @SerializedName("TimeHour")
    private ArrayList<TimeHour> timeHourArrayList;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("Message")
    private String message;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public ArrayList<TimeHour> getTimeHourArrayList() {
        return timeHourArrayList;
    }

    public void setTimeHourArrayList(ArrayList<TimeHour> timeHourArrayList) {
        this.timeHourArrayList = timeHourArrayList;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public class TimeHour {
        @SerializedName("HourMin")
        private String hourMin;
        @SerializedName("UserId")
        private int userId;
        @SerializedName("BookingId")
        private int bookingId;
        @SerializedName("AppointmentNumber")
        private String appointmentNumber;
        @SerializedName("TokenNotiDoctor")
        private String tokenNotiDoctor;
        @SerializedName("TokenNotiUser")
        private String tokenNotiUser;
        @SerializedName("UserFullName")
        private String userFullName;
        @SerializedName("UserImgProfile")
        private String userImgProfile;
        @SerializedName("ContactTypeId")
        private int contactTypeId;
        @SerializedName("ContactTypeName")
        private String contactTypeName;
        @SerializedName("ContactImg")
        private String contactImg;
        @SerializedName("IsHaveBooking")
        private boolean isHaveBooking;
        @SerializedName("IsHaveTimeReady")
        private boolean isHaveTimeReady;

        public String getAppointmentNumber() {
            return appointmentNumber;
        }

        public void setAppointmentNumber(String appointmentNumber) {
            this.appointmentNumber = appointmentNumber;
        }

        public String getTokenNotiDoctor() {
            return tokenNotiDoctor;
        }

        public void setTokenNotiDoctor(String tokenNotiDoctor) {
            this.tokenNotiDoctor = tokenNotiDoctor;
        }

        public String getTokenNotiUser() {
            return tokenNotiUser;
        }

        public void setTokenNotiUser(String tokenNotiUser) {
            this.tokenNotiUser = tokenNotiUser;
        }

        public String getHourMin() {
            return hourMin;
        }

        public void setHourMin(String hourMin) {
            this.hourMin = hourMin;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getBookingId() {
            return bookingId;
        }

        public void setBookingId(int bookingId) {
            this.bookingId = bookingId;
        }

        public String getUserFullName() {
            return userFullName;
        }

        public void setUserFullName(String userFullName) {
            this.userFullName = userFullName;
        }

        public String getUserImgProfile() {
            return userImgProfile;
        }

        public void setUserImgProfile(String userImgProfile) {
            this.userImgProfile = userImgProfile;
        }

        public int getContactTypeId() {
            return contactTypeId;
        }

        public void setContactTypeId(int contactTypeId) {
            this.contactTypeId = contactTypeId;
        }

        public String getContactTypeName() {
            return contactTypeName;
        }

        public void setContactTypeName(String contactTypeName) {
            this.contactTypeName = contactTypeName;
        }

        public String getContactImg() {
            return contactImg;
        }

        public void setContactImg(String contactImg) {
            this.contactImg = contactImg;
        }

        public boolean isHaveBooking() {
            return isHaveBooking;
        }

        public void setHaveBooking(boolean haveBooking) {
            isHaveBooking = haveBooking;
        }

        public boolean isHaveTimeReady() {
            return isHaveTimeReady;
        }

        public void setHaveTimeReady(boolean haveTimeReady) {
            isHaveTimeReady = haveTimeReady;
        }
    }
}
