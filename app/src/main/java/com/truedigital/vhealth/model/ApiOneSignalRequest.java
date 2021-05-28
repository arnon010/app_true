package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;


public class ApiOneSignalRequest extends NormalResponseObject {

    /*
    {
      "Id": -49527377,
      "UserId": -68038451,
      "UdId": "nostrud",
      "Token": "do laboris",
      "OS": "ex occaecat eiusmod ut reprehenderit",
      "Active": false,
      "NotiGroup": "consequat id deserunt"
    }
     */

    @SerializedName("Id") private int id;
    @SerializedName("UserId") private int UserId;
    @SerializedName("UdId") private String UdId;
    @SerializedName("Token") private String Token;
    @SerializedName("OS") private String OS;
    @SerializedName("Active") private boolean active;
    @SerializedName("NotiGroup") private String NotiGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUdId() {
        return UdId;
    }

    public void setUdId(String udId) {
        UdId = udId;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getNotiGroup() {
        return NotiGroup;
    }

    public void setNotiGroup(String notiGroup) {
        NotiGroup = notiGroup;
    }
}
