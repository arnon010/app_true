package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 3/31/2017 AD.
 */

public class ApiSearchBookingObject {

    @SerializedName("TotalPrice")
    private int totalPrice;
    @SerializedName("refund")
    private int refund;
    @SerializedName("PriceList")
    private ArrayList<ApiBookingNoObject.PriceList> priceList;
    @SerializedName("PaymentStatus")
    private boolean paymentStatus;
    @SerializedName("PaymentDate")
    private String paymentDate;
    @SerializedName("NotiTokenDoctor")
    private String notiTokenDoctor;
    @SerializedName("NotiTokenUser")
    private String notiTokenUser;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;
    @SerializedName("BookingId")
    private int bookingId;
    @SerializedName("AppointmentNumber")
    private String appointmentNumber;
    @SerializedName("BookingTime")
    private String bookingTime;
    @SerializedName("BookingDetail")
    private String bookingDetail;
    @SerializedName("AllergyHistory")
    private String allergyHistory;
    @SerializedName("UserId")
    private int userId;
    @SerializedName("DocId")
    private int doctorId;
    @SerializedName("ContactTypeId")
    private int contactTypeId;
    @SerializedName("DocReadyId")
    private int doctorReadyId;
    @SerializedName("TypeName")
    private int typeName;
    @SerializedName("TypePrice")
    private int typePrice;
    @SerializedName("IsCanceled")
    private boolean isCanceled;
    @SerializedName("CancelDate")
    private int cancelDate;
    @SerializedName("DiscountPrice")
    private int discountPrice;
    @SerializedName("bookingPrice")
    private int bookingPrice;

    public int getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(int bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<ApiBookingNoObject.PriceList> getPriceList() {
        return priceList;
    }

    public void setPriceList(ArrayList<ApiBookingNoObject.PriceList> priceList) {
        this.priceList = priceList;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getNotiTokenDoctor() {
        return notiTokenDoctor;
    }

    public void setNotiTokenDoctor(String notiTokenDoctor) {
        this.notiTokenDoctor = notiTokenDoctor;
    }

    public String getNotiTokenUser() {
        return notiTokenUser;
    }

    public void setNotiTokenUser(String notiTokenUser) {
        this.notiTokenUser = notiTokenUser;
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

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public String getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(String bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
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

    public int getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(int contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public int getDoctorReadyId() {
        return doctorReadyId;
    }

    public void setDoctorReadyId(int doctorReadyId) {
        this.doctorReadyId = doctorReadyId;
    }

    public int getTypeName() {
        return typeName;
    }

    public void setTypeName(int typeName) {
        this.typeName = typeName;
    }

    public int getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(int typePrice) {
        this.typePrice = typePrice;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public int getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(int cancelDate) {
        this.cancelDate = cancelDate;
    }
}
