package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorLangInformationDao {

    @SerializedName("DoctorLangId") private int doctorLangId;
    @SerializedName("DoctorId") private int doctorId;
    @SerializedName("LanguageCode") private String languageCode;
    @SerializedName("LanguageName") private String languageName;
    @SerializedName("IsRequired") private boolean required;
    @SerializedName("TitleId") private int titleId;
    @SerializedName("Title") private String title;
    @SerializedName("StudyTitleId") private int studyTitleId;
    @SerializedName("StudyTitle") private String studyTitle;
    @SerializedName("FirstName") private String firstName;
    @SerializedName("LastName") private String lastName;
    @SerializedName("SpecialityDescription") private String specialityDescription;
    @SerializedName("CertificateDetail") private String certificateDetail;
    @SerializedName("CareerHistory") private String careerHistory;
    @SerializedName("IsFavorite") private boolean favorite;
    @SerializedName("StandByImage") private String standByImage;

    public int getDoctorLangId() {
        return doctorLangId;
    }

    public void setDoctorLangId(int doctorLangId) {
        this.doctorLangId = doctorLangId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStudyTitleId() {
        return studyTitleId;
    }

    public void setStudyTitleId(int studyTitleId) {
        this.studyTitleId = studyTitleId;
    }

    public String getStudyTitle() {
        return studyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        this.studyTitle = studyTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialityDescription() {
        return specialityDescription;
    }

    public void setSpecialityDescription(String specialityDescription) {
        this.specialityDescription = specialityDescription;
    }

    public String getCertificateDetail() {
        return certificateDetail;
    }

    public void setCertificateDetail(String certificateDetail) {
        this.certificateDetail = certificateDetail;
    }

    public String getCareerHistory() {
        return careerHistory;
    }

    public void setCareerHistory(String careerHistory) {
        this.careerHistory = careerHistory;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getStandByImage() {
        return standByImage;
    }

    public void setStandByImage(String standByImage) {
        this.standByImage = standByImage;
    }


}
