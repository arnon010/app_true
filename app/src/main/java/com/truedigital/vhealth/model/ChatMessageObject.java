package com.truedigital.vhealth.model;

import android.net.Uri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;

public class ChatMessageObject {

    private String messageText;
    private String imageProfile;

    private String AttachmentUrl;
    private String AttachmentThumbnailUrl;
    private boolean attachFile;

    private Boolean remote;
    private String timeMessage;
    public int type;

    private boolean uploadFail;
    private Uri imageUri;
    private boolean showProgress;
    private int percentLoading;
    private boolean isSending;

    public ChatMessageObject() {
    }

    public ChatMessageObject(String messageText) {
        this.messageText = messageText;
        remote = false;
        this.uploadFail = false;
    }

    public ChatMessageObject(String messageText, String imageProfile) {
        this.messageText = messageText;
        this.imageProfile = imageProfile;
        remote = false;
        this.uploadFail = false;
    }

    public ChatMessageObject(String messageText, String imageProfile, String timeMessage, int type, String attachmentUrl, String attachmentThumbnailUrl) {
        this.messageText = messageText;
        this.imageProfile = imageProfile;
        this.timeMessage = timeMessage;
        this.type = type;
        remote = false;
        this.uploadFail = false;

        this.AttachmentUrl = attachmentUrl;
        this.AttachmentThumbnailUrl = attachmentThumbnailUrl;

    }


    public String getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(String timeMessage) {
        this.timeMessage = timeMessage;
    }

    public static ChatMessageObject fromData(String messageData) {
        return new ChatMessageObject(messageData);
    }

    public String getMessageText() {
        return messageText;
    }

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setMessageText(String mMessageText) {
        this.messageText = mMessageText;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public static String beanToString(ChatMessageObject object){

        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringEmp = new StringWriter();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            objectMapper.writeValue(stringEmp, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringEmp.toString();
    }

    public static <T> T stringToBean(String content, Class<T> valueType) throws IOException {
        return new ObjectMapper().readValue(content, valueType);
    }

    public boolean isAttachFile() {
        return attachFile;
    }

    public void setAttachFile(boolean attachFile) {
        this.attachFile = attachFile;
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

    public boolean isUploadFail() {
        return uploadFail;
    }

    public void setUploadFail(boolean uploadFail) {
        this.uploadFail = uploadFail;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public int getPercentLoading() {
        return percentLoading;
    }

    public void setPercentLoading(int percentLoading) {
        this.percentLoading = percentLoading;
    }

    public boolean isSending() {
        return isSending;
    }

    public void setSending(boolean sending) {
        isSending = sending;
    }
}
