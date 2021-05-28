package com.truedigital.vhealth.model;

import java.util.ArrayList;

/**
 * Created by nilecon on 2/22/2017 AD.
 */

public class AppointmentListObjectBase {

    private String dateTimeTitle;

    private String hourMinute;
    private int userId;
    private int doctorId;
    private int bookingId;
    private String appointmentNumber;
    private String tokenNotiDoctor;
    private String tokenNotiUser;
    private String fullName;
    private String imageProfile;
    private int contactTypeId;
    private String contactTypeName;
    private String contactImage;
    private String detail;
    private int beforeTime;
    private ArrayList<ApiDoctorObject.AccountObject.Skill> specialtyList;
    private ArrayList<ApiDoctorObject.AccountObject.Skill> subSpecialtyList;

    public int type;

    public int getBeforeTime() {
        return beforeTime;
    }

    public void setBeforeTime(int beforeTime) {
        this.beforeTime = beforeTime;
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

    public String getDateTimeTitle() {
        return dateTimeTitle;
    }

    public void setDateTimeTitle(String dateTimeTitle) {
        this.dateTimeTitle = dateTimeTitle;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
