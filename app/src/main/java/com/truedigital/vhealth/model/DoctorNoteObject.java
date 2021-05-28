package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class DoctorNoteObject {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("AppointmentNumber")
    private String appointmentNumber;
    @SerializedName("AppointmentTime")
    private String appointmentTimeString;
    private Date appointmentTime;
    @SerializedName("DoctorId")
    private int doctorId;
    @SerializedName("DoctorName")
    private String doctorName;
    @SerializedName("DoctorImage")
    private String doctorImage;
    @SerializedName("CategoryName")
    private String categoryName;
    @SerializedName("ShortNote")
    private String shortNote;
    @SerializedName("EndOfEffectiveDate")
    private String endOfEffectiveDateString;
    private Date endOfEffectiveDate;
    @SerializedName("ContactMinutes")
    private int contactMinutes;
    @SerializedName("ShortNoteAttachmentList")
    private ArrayList<ShortNoteAttachmentObject> shortNoteAttachmentList;
    @SerializedName("RecommendProductList")
    private ArrayList<RecommendProductObject> recommendProductList;
    @SerializedName("RecommendMedicineList")
    private ArrayList<RecommendMedicationObject> recommendMedicineList;

    private String productCouponsCode;
    private float discountProductCouponsPrice;
    private String medicineCouponsCode;
    private float discountMedicineCouponsPrice;
    private boolean isConfirmProductCouponsCode;
    private boolean isConfirmMedicineCouponsCode;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
        if(appointmentTime != null)
        {
            this.appointmentTimeString = ConvertDate.convertDateToServiceFormat(appointmentTime);
        }
        else
        {
            this.appointmentTimeString = null;
        }
    }

    public String getAppointmentTimeString() {
        return appointmentTimeString;
    }

    public void setAppointmentTimeString(String appointmentTimeString) {
        this.appointmentTimeString = appointmentTimeString;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getShortNote() {
        return shortNote;
    }

    public void setShortNote(String shortNote) {
        this.shortNote = shortNote;
    }

    public String getEndOfEffectiveDateString() {
        return endOfEffectiveDateString;
    }

    public void setEndOfEffectiveDateString(String endOfEffectiveDateString) {
        this.endOfEffectiveDateString = endOfEffectiveDateString;
    }

    public Date getEndOfEffectiveDate() {
        return endOfEffectiveDate;
    }

    public void setEndOfEffectiveDate(Date endOfEffectiveDate) {
        this.endOfEffectiveDate = endOfEffectiveDate;
        if(endOfEffectiveDate != null)
        {
            this.endOfEffectiveDateString = ConvertDate.convertDateToServiceFormat(endOfEffectiveDate);
        }
        else
        {
            this.endOfEffectiveDateString = null;
        }
    }

    public int getContactMinutes() {
        return this.contactMinutes;
    }

    public void setContactMinutes(int contactMinutes) {
        this.contactMinutes = contactMinutes;
    }

    public ArrayList<ShortNoteAttachmentObject> getShortNoteAttachmentList() {
        return shortNoteAttachmentList;
    }

    public void setShortNoteAttachmentList(ArrayList<ShortNoteAttachmentObject> shortNoteAttachmentList) {
        this.shortNoteAttachmentList = shortNoteAttachmentList;
    }

    public ArrayList<RecommendProductObject> getRecommendProductList() {
        return recommendProductList;
    }

    public void setRecommendProductList(ArrayList<RecommendProductObject> recommendProductList) {
        this.recommendProductList = recommendProductList;
    }

    public ArrayList<RecommendMedicationObject> getRecommendMedicineList() {
        return recommendMedicineList;
    }

    public void setRecommendMedicineList(ArrayList<RecommendMedicationObject> recommendMedicineList) {
        this.recommendMedicineList = recommendMedicineList;
    }

    public String getProductCouponsCode() {
        return productCouponsCode;
    }

    public void setProductCouponsCode(String productCouponsCode) {
        this.productCouponsCode = productCouponsCode;
    }

    public float getDiscountProductCouponsPrice() {
        return discountProductCouponsPrice;
    }

    public void setDiscountCouponsPrice(float discountProductCouponsPrice) {
        this.discountProductCouponsPrice = discountProductCouponsPrice;
    }

    public String getMedicineCouponsCode() {
        return medicineCouponsCode;
    }

    public void setMedicineCouponsCode(String medicineCouponsCode) {
        this.medicineCouponsCode = medicineCouponsCode;
    }

    public float getDiscountMedicineCouponsPrice() {
        return discountMedicineCouponsPrice;
    }

    public void setDiscountMedicineCouponsPrice(float discountMedicineCouponsPrice) {
        this.discountMedicineCouponsPrice = discountMedicineCouponsPrice;
    }

    public boolean getIsConfirmProductCouponsCode() {
        return isConfirmProductCouponsCode;
    }

    public void setIsConfirmProductCouponsCode(boolean isConfirmProductCouponsCode) {
        this.isConfirmProductCouponsCode = isConfirmProductCouponsCode;
    }

    public boolean getIsConfirmMedicineCouponsCode() {
        return isConfirmMedicineCouponsCode;
    }

    public void setIsConfirmMedicineCouponsCode(boolean isConfirmMedicineCouponsCode) {
        this.isConfirmMedicineCouponsCode = isConfirmMedicineCouponsCode;
    }

}

