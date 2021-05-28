package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorLangsDao extends NormalResponseObject {

    @SerializedName("DoctorId") private int doctorId;
    @SerializedName("LanguageCode") private String LanguageCode;
    @SerializedName("Title") private String Title;
    @SerializedName("StudyTitle") private String StudyTitle;
    @SerializedName("FirstName") private String FirstName;
    @SerializedName("LastName") private String LastName;
    @SerializedName("SpecialtyDescription") private String SpecialtyDescription;
    @SerializedName("CertificateDetail") private String CertificateDetail;
    @SerializedName("CareerHistory") private String CareerHistory;
    @SerializedName("ContactMinute") private int ContactMinute;
    @SerializedName("OnlyShowInClinic") private boolean OnlyShowInClinic;
    @SerializedName("Active") private boolean Active;
    @SerializedName("Address") private String address;
    @SerializedName("CareerCertificationNumber") private String careerCertificationNumber;

    public ItemDoctorLangsDao() {

    }

    public ItemDoctorLangsDao(String title, String studyTitle, String firstName, String lastName, String specialtyDescription, String certificateDetail, String careerHistory) {
        Title = title;
        StudyTitle = studyTitle;
        FirstName = firstName;
        LastName = lastName;
        SpecialtyDescription = specialtyDescription;
        CertificateDetail = certificateDetail;
        CareerHistory = careerHistory;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStudyTitle() {
        return StudyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        StudyTitle = studyTitle;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getSpecialtyDescription() {
        return SpecialtyDescription;
    }

    public void setSpecialtyDescription(String specialtyDescription) {
        SpecialtyDescription = specialtyDescription;
    }

    public String getCertificateDetail() {
        return CertificateDetail;
    }

    public void setCertificateDetail(String certificateDetail) {
        CertificateDetail = certificateDetail;
    }

    public String getCareerHistory() {
        return CareerHistory;
    }

    public void setCareerHistory(String careerHistory) {
        CareerHistory = careerHistory;
    }

    public int getContactMinute() {
        return ContactMinute;
    }

    public void setContactMinute(int contactMinute) {
        ContactMinute = contactMinute;
    }

    public boolean isOnlyShowInClinic() {
        return OnlyShowInClinic;
    }

    public void setOnlyShowInClinic(boolean onlyShowInClinic) {
        OnlyShowInClinic = onlyShowInClinic;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCareerCertificationNumber() {
        return careerCertificationNumber;
    }

    public void setCareerCertificationNumber(String careerCertificationNumber) {
        this.careerCertificationNumber = careerCertificationNumber;
    }
}
