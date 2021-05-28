package com.truedigital.vhealth.model;

/**
 * Created by nilecon on 4/21/2017 AD.
 */

public class PaymentHistoryObjectBase {
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_BODY = 2;

    //for header
    private String dateTimeTitle;

    //for body
    private String time;
    private String appointmentNumber;
    private String bookingTime;
    private int userId;
    private int doctorId;
    private String imgProfileDoctor;
    private String doctorName;
    private int contactTypeId;
    private int totalPrice;
    private String paymentStatus;

    public int type;

    public PaymentHistoryObjectBase() {
    }

    //HEADER
    public PaymentHistoryObjectBase(String dateTimeTitle, int type) {
        this.dateTimeTitle = dateTimeTitle;
        this.type = type;
    }

    //BODY
    public PaymentHistoryObjectBase(String time,
                                    String appointmentNumber,
                                    String bookingTime,
                                    int userId,
                                    int doctorId,
                                    String imgProfileDoctor,
                                    String doctorName,
                                    int contactTypeId,
                                    int totalPrice,
                                    String paymentStatus,
                                    int type) {
        this.time = time;
        this.appointmentNumber = appointmentNumber;
        this.bookingTime = bookingTime;
        this.userId = userId;
        this.doctorId = doctorId;
        this.imgProfileDoctor = imgProfileDoctor;
        this.doctorName = doctorName;
        this.contactTypeId = contactTypeId;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.type = type;
    }

    public String getDateTimeTitle() {
        return dateTimeTitle;
    }

    public void setDateTimeTitle(String dateTimeTitle) {
        this.dateTimeTitle = dateTimeTitle;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
