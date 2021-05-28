package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemArticleDao extends NormalResponseObject {
    /*
      "ArticleId": 1,
              "ArticleGroupId": 1,
              "GroupType": "Article",
              "Title": "สวัสดีชาวโลก",
              "ShortDescription": "พวกเรามาเยือนแล้ว",
              "Description": "สวัสดีชาวโลก พวกเรามาเยือนแล้ว",
              "Active": true,
              "CoverImage": "http://apps.softsquaregroup.com/ChiiwiiBO/Warehouse/Gallery/chiiwii-live-logo.bak.png",
              "VideoSampleImage": "http://apps.softsquaregroup.com/ChiiwiiBO/Warehouse/Gallery/chiiwii-live-logo.bak.png",
              "ArticleVideo": null,
              "SendNotification": true,
              "EffectiveDate": "2008-09-15T15:53:00",
              "EndOfEffectiveDate": "2008-09-20T15:53:00",
              "NotificationDate": "2008-09-15T15:53:00"

    */

    /*
    {"ArticleGroupId":1,
            "Active": true,
            "SendNotification": true,
            "IsApprove":false,
            "IsBanner":true,
            "EffectiveDate":"2018-11-09T09:10:46",
            "NotificationDate":"2018-11-09T09:10:46",
            "EndOfEffectiveDate":"2019-05-09T09:10:46"},
            */

    @SerializedName("Id") private int Id;
    @SerializedName("ArticleId") private int ArticleId;
    @SerializedName("ArticleGroupId") private int ArticleGroupId;
    @SerializedName("DoctorId") private int DoctorId;
    @SerializedName("GroupType") private String GroupType;
    @SerializedName("Title") private String Title;
    @SerializedName("ShortDescription") private String ShortDescription;
    @SerializedName("Description") private String Description;
    @SerializedName("Active") private boolean active;
    @SerializedName("CoverImage") private String CoverImage;
    @SerializedName("VideoSampleImage") private String VideoSampleImage;
    @SerializedName("ArticleVideo") private String ArticleVideo;
    @SerializedName("SendNotification") private boolean SendNotification;
    @SerializedName("IsApprove") private boolean IsApprove;
    @SerializedName("IsBanner") private boolean IsBanner;
    @SerializedName("EffectiveDate") private String EffectiveDate;
    @SerializedName("NotificationDate") private String NotificationDate;
    @SerializedName("EndOfEffectiveDate") private String EndOfEffectiveDate;
    @SerializedName("DoctorName") private String DoctorName;
    @SerializedName("BannerImage") private String bannerImage;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getArticleId() {
        return ArticleId;
    }

    public void setArticleId(int articleId) {
        ArticleId = articleId;
    }

    public int getArticleGroupId() {
        return ArticleGroupId;
    }

    public void setArticleGroupId(int articleGroupId) {
        ArticleGroupId = articleGroupId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public String getGroupType() {
        return GroupType;
    }

    public void setGroupType(String groupType) {
        GroupType = groupType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCoverImage() {
        return CoverImage;
    }

    public void setCoverImage(String coverImage) {
        CoverImage = coverImage;
    }

    public String getVideoSampleImage() {
        return VideoSampleImage;
    }

    public void setVideoSampleImage(String videoSampleImage) {
        VideoSampleImage = videoSampleImage;
    }

    public String getArticleVideo() {
        return ArticleVideo;
    }

    public void setArticleVideo(String articleVideo) {
        ArticleVideo = articleVideo;
    }

    public boolean isSendNotification() {
        return SendNotification;
    }

    public void setSendNotification(boolean sendNotification) {
        SendNotification = sendNotification;
    }

    public boolean isApprove() {
        return IsApprove;
    }

    public void setApprove(boolean approve) {
        IsApprove = approve;
    }

    public boolean isBanner() {
        return IsBanner;
    }

    public void setBanner(boolean banner) {
        IsBanner = banner;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public String getNotificationDate() {
        return NotificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        NotificationDate = notificationDate;
    }

    public String getEndOfEffectiveDate() {
        return EndOfEffectiveDate;
    }

    public void setEndOfEffectiveDate(String endOfEffectiveDate) {
        EndOfEffectiveDate = endOfEffectiveDate;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }
}
