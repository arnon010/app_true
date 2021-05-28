package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 4/21/2017 AD.
 */

public class ApiPaymentHistoryObject {

    @SerializedName("DateTimeAppointments")
    private ArrayList<DateTimeAppointmentObject> dateTimeAppointmentObjects;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;
    @SerializedName("Message")
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DateTimeAppointmentObject> getDateTimeAppointmentObjects() {
        return dateTimeAppointmentObjects;
    }

    public void setDateTimeAppointmentObjects(ArrayList<DateTimeAppointmentObject> dateTimeAppointmentObjects) {
        this.dateTimeAppointmentObjects = dateTimeAppointmentObjects;
    }

    public class DateTimeAppointmentObject {

        @SerializedName("DateTimeTitle")
        private String dateTimeTitle;
        @SerializedName("Appointments")
        private ArrayList<AppointmentObject> appointmentObjects;

        public String getDateTimeTitle() {
            return dateTimeTitle;
        }

        public void setDateTimeTitle(String dateTimeTitle) {
            this.dateTimeTitle = dateTimeTitle;
        }

        public ArrayList<AppointmentObject> getAppointmentObjects() {
            return appointmentObjects;
        }

        public void setAppointmentObjects(ArrayList<AppointmentObject> appointmentObjects) {
            this.appointmentObjects = appointmentObjects;
        }

        public class AppointmentObject{
            @SerializedName("HourMin")
            private String time;
            @SerializedName("AppointmentNumber")
            private String appointmentNumber;
            @SerializedName("BookingTime")
            private String bookingTime;
            @SerializedName("UserId")
            private int userId;
            @SerializedName("DocId")
            private int doctorId;
            @SerializedName("DoctorImgProfile")
            private String imgProfileDoctor;
            @SerializedName("DoctorName")
            private String doctorName;
            @SerializedName("ContactTypeId")
            private int contactTypeId;
            @SerializedName("TotalPrice")
            private int totalPrice;
            @SerializedName("PaymentStatus")
            private String paymentStatus;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getAppointmentNumber() {
                return appointmentNumber;
            }

            public void setAppointmentNumber(String appointmentNumber) {
                this.appointmentNumber = appointmentNumber;
            }

            public String getBookingTime() {
                return bookingTime;
            }

            public void setBookingTime(String bookingTime) {
                this.bookingTime = bookingTime;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(int doctorId) {
                this.doctorId = doctorId;
            }

            public String getImgProfileDoctor() {
                return imgProfileDoctor;
            }

            public void setImgProfileDoctor(String imgProfileDoctor) {
                this.imgProfileDoctor = imgProfileDoctor;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public int getContactTypeId() {
                return contactTypeId;
            }

            public void setContactTypeId(int contactTypeId) {
                this.contactTypeId = contactTypeId;
            }

            public int getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(int totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getPaymentStatus() {
                return paymentStatus;
            }

            public void setPaymentStatus(String paymentStatus) {
                this.paymentStatus = paymentStatus;
            }
        }
    }
}
