package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 2/14/2017 AD.
 */

public class ApiNewsObject {

    @SerializedName("ActivityList")
    private ArrayList<Lists> newsList;
    @SerializedName("Activity")
    private Lists newsObject;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public Lists getNewsObject() {
        return newsObject;
    }

    public void setNewsObject(Lists newsObject) {
        this.newsObject = newsObject;
    }

    public ArrayList<Lists> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<Lists> newsList) {
        this.newsList = newsList;
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

    public class Lists{

        @SerializedName("ActivityId")
        private int newsId;
        @SerializedName("CoverImage")
        private String coverImage;
        @SerializedName("Title")
        private String title;
        @SerializedName("ShotDescription")
        private String shotDescription;
        @SerializedName("Description")
        private String description;
        @SerializedName("Link")
        private String link;
        @SerializedName("Date")
        private String date;

        public String getShotDescription() {
            return shotDescription;
        }

        public void setShotDescription(String shotDescription) {
            this.shotDescription = shotDescription;
        }

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
