package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 2/22/2017 AD.
 */

public class ApiAppointmentListObject {

    @SerializedName("DateTimeAppointments")
    private ArrayList<AppointmentDateList> appointmentDateArrayList;
    @SerializedName("Message")
    private String message;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public ArrayList<AppointmentDateList> getAppointmentDateArrayList() {
        return appointmentDateArrayList;
    }

    public void setAppointmentDateArrayList(ArrayList<AppointmentDateList> appointmentDateArrayList) {
        this.appointmentDateArrayList = appointmentDateArrayList;
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

    public class AppointmentDateList{
        @SerializedName("DateTimeTitle")
        private String dateTimeTitle;
        @SerializedName("Appointments")
        private ArrayList<Appointments> appointmentsArrayList;

        public String getDateTimeTitle() {
            return dateTimeTitle;
        }

        public void setDateTimeTitle(String dateTimeTitle) {
            this.dateTimeTitle = dateTimeTitle;
        }

        public ArrayList<Appointments> getAppointmentsArrayList() {
            return appointmentsArrayList;
        }

        public void setAppointmentsArrayList(ArrayList<Appointments> appointmentsArrayList) {
            this.appointmentsArrayList = appointmentsArrayList;
        }

        public class Appointments{
            @SerializedName("HourMin")
            private String hourMinute;
            @SerializedName("UserId")
            private int userId;
            @SerializedName("DocId")
            private int doctorId;
            @SerializedName("BookingId")
            private int bookingId;
            @SerializedName("AppointmentNumber")
            private String appointmentNumber;
            @SerializedName("TokenNotiDoctor")
            private String tokenNotiDoctor;
            @SerializedName("TokenNotiUser")
            private String tokenNotiUser;
            @SerializedName("UserName")
            private String username;
            @SerializedName("UserFullName")
            private String fullName;
            @SerializedName("UserImgProfile")
            private String imageProfile;
            @SerializedName("ContactTypeId")
            private int contactTypeId;
            @SerializedName("ContactTypeName")
            private String contactTypeName;
            @SerializedName("ContactImg")
            private String contactImage;
            @SerializedName("Detail")
            private String detail;
            @SerializedName("BeforeTime")
            private int beforeTime;

            public int getBeforeTime() {
                return beforeTime;
            }

            public void setBeforeTime(int beforeTime) {
                this.beforeTime = beforeTime;
            }

            //for user
            @SerializedName("Specialty")
            private ArrayList<ApiDoctorObject.AccountObject.Skill> specialtyList;
            @SerializedName("SubSpecialty")
            private ArrayList<ApiDoctorObject.AccountObject.Skill> subSpecialtyList;

            public ArrayList<ApiDoctorObject.AccountObject.Skill> getSpecialtyList() {
                return specialtyList;
            }

            public void setSpecialtyList(ArrayList<ApiDoctorObject.AccountObject.Skill> specialtyList) {
                this.specialtyList = specialtyList;
            }

            public ArrayList<ApiDoctorObject.AccountObject.Skill> getSubSpecialtyList() {
                return subSpecialtyList;
            }

            public void setSubSpecialtyList(ArrayList<ApiDoctorObject.AccountObject.Skill> subSpecialtyList) {
                this.subSpecialtyList = subSpecialtyList;
            }

            public int getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(int doctorId) {
                this.doctorId = doctorId;
            }

            public String getHourMinute() {
                return hourMinute;
            }

            public void setHourMinute(String hourMinute) {
                this.hourMinute = hourMinute;
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

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public String getImageProfile() {
                return imageProfile;
            }

            public void setImageProfile(String imageProfile) {
                this.imageProfile = imageProfile;
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

            public String getContactImage() {
                return contactImage;
            }

            public void setContactImage(String contactImage) {
                this.contactImage = contactImage;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

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

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
