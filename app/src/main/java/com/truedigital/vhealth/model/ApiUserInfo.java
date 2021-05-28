package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiUserInfo extends NormalResponseObject{

    @SerializedName("UserId") private int userId;
    @SerializedName("Username") private String userName;
    @SerializedName("Email") private String email;
    @SerializedName("FirstName") private String firstName;
    @SerializedName("LastName") private String lastName;
    @SerializedName("DefaultLanguageCode") private String DefaultLanguageCode;
    @SerializedName("ProfileImage") private String ProfileImage;
    @SerializedName("PatientId") private int PatientId;
    @SerializedName("DoctorId") private int DoctorId;
    @SerializedName("isVerify") private boolean isVerify;
    @SerializedName("Phone") private String phone;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDefaultLanguageCode() {
        return DefaultLanguageCode;
    }

    public void setDefaultLanguageCode(String defaultLanguageCode) {
        DefaultLanguageCode = defaultLanguageCode;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public void setVerify(boolean verify) {
        isVerify = verify;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
