package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nilecon on 4/10/2017 AD.
 */

public class ApiContactObject {

    @SerializedName("ContactPrice")
    private int contactPrice;

    @SerializedName("IsChat")
    private boolean isChat;
    @SerializedName("IsVoice")
    private boolean isVoice;
    @SerializedName("IsVideo")
    private boolean isVideo;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public boolean isChat() {
        return isChat;
    }

    public void setChat(boolean chat) {
        isChat = chat;
    }

    public boolean isVoice() {
        return isVoice;
    }

    public void setVoice(boolean voice) {
        isVoice = voice;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public int getContactPrice() {
        return contactPrice;
    }

    public void setContactPrice(int contactPrice) {
        this.contactPrice = contactPrice;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
