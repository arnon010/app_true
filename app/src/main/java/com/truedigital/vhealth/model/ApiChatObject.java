package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiChatObject extends NormalResponseObject {

    @SerializedName("Id") private int id;
    @SerializedName("AppointmentNumber") private String appointmentNumber;
    @SerializedName("UserId") private int userId;
    @SerializedName("Message") private String message;
    @SerializedName("MessageTime") private String messageTime;

    @SerializedName("AttachmentUrl") private String AttachmentUrl;
    @SerializedName("AttachmentThumbnailUrl") private String AttachmentThumbnailUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getAttachmentUrl() {
        return AttachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        AttachmentUrl = attachmentUrl;
    }

    public String getAttachmentThumbnailUrl() {
        return AttachmentThumbnailUrl;
    }

    public void setAttachmentThumbnailUrl(String attachmentThumbnailUrl) {
        AttachmentThumbnailUrl = attachmentThumbnailUrl;
    }
}
