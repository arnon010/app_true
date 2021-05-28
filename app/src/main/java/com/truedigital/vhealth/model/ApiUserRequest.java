package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiUserRequest extends NormalResponseObject{

    @SerializedName("UserId") private int userId;
    @SerializedName("Username") private String userName;
    @SerializedName("OldPasswordl") private String OldPassword;
    @SerializedName("Password") private String Password;
    @SerializedName("ConfirmPassword") private String ConfirmPassword;
    @SerializedName("ReferenceCode") private String ReferenceCode;
    @SerializedName("OTP") private String OTP;

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

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getReferenceCode() {
        return ReferenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        ReferenceCode = referenceCode;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
