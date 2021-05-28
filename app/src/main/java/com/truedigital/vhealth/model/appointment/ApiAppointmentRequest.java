package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.ItemAntiMedicine;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiAppointmentRequest {

    /*
    {
        "AppointmentTime": "2018-10-11T14:00:00",
            "PatientId": 2542,
            "DoctorId": 16,
            "TypeCode": "VO",
            "ContactTelephone": "012312441",
            "ContactEmail": "songkritr@gmail.com",
            "DiscountCode": "iGnorE",
            "MedicineAllergy": "[\n  \"test\",\n  \"test2\"\n]",
            "IsAllowEHR": true,
            "PaymentCode": "",
            "OmiseToken": "tokn_test_5ddioxozltvxz2q9kxf"
    }
    */
    // MedicineAllergy need serialize string  from string array

    @SerializedName("AppointmentTime")
    private String appointmentTime;
    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("DoctorId")
    private int doctorId;
    @SerializedName("TypeCode")
    private String typeCode;
    @SerializedName("ContactTelephone")
    private String contactTelephone;
    @SerializedName("ContactEmail")
    private String contactEmail;
    @SerializedName("DiscountCode")
    private String discountCode;

    @SerializedName("Detail")
    private String detail;

    @SerializedName("MedicineAllergy")
    private String medicineAllergy;
    @SerializedName("IsAllowEHR")
    private Boolean isAllowEHR;
    @SerializedName("PaymentCode")
    private String paymentCode;
    @SerializedName("OmiseToken")
    private String omiseToken;

    @SerializedName("AppointmentNumber")
    private String AppointmentNumber;

    @SerializedName("isRememberCard")
    private boolean isRememberCard;

    @SerializedName("Fingerprint")
    private String Fingerprint;

    @SerializedName("CardId")
    private int CardId;

    @SerializedName("SlipUrl") private String slipUrl;

    private List<String> listMedicineAllergy;
    private List<ItemAntiMedicine> listItemAntiMedicine;

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMedicineAllergy() {
        return medicineAllergy;
    }

    public void setMedicineAllergy(String medicineAllergy) {
        this.medicineAllergy = medicineAllergy;
    }

    public Boolean getAllowEHR() {
        return isAllowEHR;
    }

    public void setAllowEHR(Boolean allowEHR) {
        isAllowEHR = allowEHR;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getOmiseToken() {
        return omiseToken;
    }

    public void setOmiseToken(String omiseToken) {
        this.omiseToken = omiseToken;
    }

    public String getAppointmentNumber() {
        return AppointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        AppointmentNumber = appointmentNumber;
    }

    public List<String> getListMedicineAllergy() {
        return listMedicineAllergy;
    }

    public void setListMedicineAllergy(List<String> listMedicineAllergy) {
        this.listMedicineAllergy = listMedicineAllergy;
    }

    public List<ItemAntiMedicine> getListItemAntiMedicine() {
        return listItemAntiMedicine;
    }

    public void setListItemAntiMedicine(List<ItemAntiMedicine> listItemAntiMedicine) {
        this.listItemAntiMedicine = listItemAntiMedicine;
    }

    public boolean isRememberCard() {
        return isRememberCard;
    }

    public void setRememberCard(boolean rememberCard) {
        isRememberCard = rememberCard;
    }

    public String getFingerprint() {
        return Fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        Fingerprint = fingerprint;
    }

    public int getCardId() {
        return CardId;
    }

    public void setCardId(int cardId) {
        CardId = cardId;
    }

    public String getSlipUrl() {
        return slipUrl;
    }

    public void setSlipUrl(String slipUrl) {
        this.slipUrl = slipUrl;
    }
}
