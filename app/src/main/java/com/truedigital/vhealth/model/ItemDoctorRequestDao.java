package com.truedigital.vhealth.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorRequestDao extends NormalResponseObject {


    @SerializedName("Email") private String Email;
    @SerializedName("Password") private String Password;
    @SerializedName("ConfirmPassword") private String ConfirmPassword;
    @SerializedName("Phone") private String Phone;
    @SerializedName("OnlyShowInClinic") private boolean OnlyShowInClinic;
    @SerializedName("Active") private boolean Active;
    @SerializedName("IsVerify") private boolean IsVerify;
    @SerializedName("CurrencyCode") private String CurrencyCode;
    @SerializedName("Price") private int Price;
    @SerializedName("Address") private String address;
    @SerializedName("CareerCertificationNumber") private String careerCertificationNumber;
    @SerializedName("UriProfileImage") private Uri uriImage;
                /*
                "Email": "para123@gmail.com",
                "Password" : "123456",
                "ConfirmPassword" : "123456",
                "Phone": "0881188188",
                "OnlyShowInClinic" : false,
                "Active" : true,
                "IsVerify" : true,
                "CurrencyCode":"BTH",
                "Price":500
                */

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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

    public boolean isVerify() {
        return IsVerify;
    }

    public void setVerify(boolean verify) {
        IsVerify = verify;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
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

    public Uri getUriImage() {
        return uriImage;
    }

    public void setUriImage(Uri uriImage) {
        this.uriImage = uriImage;
    }
}
