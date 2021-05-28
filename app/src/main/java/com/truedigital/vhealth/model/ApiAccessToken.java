package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songkrit on 2/6/2017 AD.
 */

public class ApiAccessToken extends NormalResponseObject {

    /*
    "username": "",
            "userId": "57118",
            "firstname": "rtrtrtrt",
            "lastname": "yuyuyuy",
            "email": "abcabc@gmail.com",
            "Telephone": "0814217782",
            "isAcceptAgreement": "False",
            "clientId": "c3c67514-c50e-46f0-9a5b-2e38eef34c1b",
            "clientName": "Chiiwii LIVE Provider",
            "isVerify": "False",
            "doctorId": "5379",
            "fullname": "Mr.ทพ. rtrtrtrt yuyuyuy",
            "profileImage": "https://dev-api.chiiwiilive.com/WebService_2.0.0/api/attachments/Doctors/5379?updatetime=636812662366830000",

    */

    @SerializedName("access_token") private String access_token;
    @SerializedName("refresh_token") private String refresh_token;
    @SerializedName("token_type") private String token_type;

    @SerializedName("userId") private int userId;
    @SerializedName("username") private String userName;

    @SerializedName("firstname") private String firstname;
    @SerializedName("lastname") private String lastname;
    @SerializedName("email") private String email;
    @SerializedName("Telephone") private String Telephone;
    @SerializedName("isAcceptAgreement") private boolean isAcceptAgreement;

    @SerializedName("clientId") private String clientId;
    @SerializedName("clientName") private String clientName;
    @SerializedName("isVerify") private boolean isVerify;
    @SerializedName("doctorId") private int doctorId;
    @SerializedName("patientId") private int patientId;
    @SerializedName("fullname") private String fullname;
    @SerializedName("profileImage") private String profileImage;

    public ApiAccessToken() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public boolean isAcceptAgreement() {
        return isAcceptAgreement;
    }

    public void setAcceptAgreement(boolean acceptAgreement) {
        isAcceptAgreement = acceptAgreement;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public void setVerify(boolean verify) {
        isVerify = verify;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
