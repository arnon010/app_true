package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemProductDao extends NormalResponseObject {

    @SerializedName("Id") private int id;

    @SerializedName("ProductGroupId") private int ProductGroupId;
    @SerializedName("ProductGroupName") private String ProductGroupName;
    @SerializedName("Active") private boolean active;
    @SerializedName("Title") private String Title;
    @SerializedName("ShortDescription") private String ShortDescription;
    @SerializedName("Description") private String Description;
    @SerializedName("CoverImage") private String CoverImage;
    @SerializedName("VideoSampleImage") private String VideoSampleImage;
    @SerializedName("PromotionVideo") private String PromotionVideo;
    @SerializedName("SendNotification") private boolean SendNotification;
    @SerializedName("EffectiveDate") private String EffectiveDate;
    @SerializedName("EndOfEffectiveDate") private String EndOfEffectiveDate;
    @SerializedName("NotificationDate") private String NotificationDate;
    @SerializedName("NormalSellingPrice") private int NormalSellingPrice;
    @SerializedName("PromotionSellingPrice") private int PromotionSellingPrice;


                        //"NormalSellingPrice": 300,
                        //        "PromotionSellingPrice": 150,

    @SerializedName("ProductId") private int ProductId;
    @SerializedName("MethodId") private int MethodId;
    @SerializedName("MethodDescription") private String MethodDescription;
    @SerializedName("ProductName")private String ProductName;
    @SerializedName("Quantity") private int Quantity;
    @SerializedName("Unit") private String Unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductGroupId() {
        return ProductGroupId;
    }

    public void setProductGroupId(int productGroupId) {
        ProductGroupId = productGroupId;
    }

    public String getProductGroupName() {
        return ProductGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        ProductGroupName = productGroupName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getPromotionVideo() {
        return PromotionVideo;
    }

    public void setPromotionVideo(String promotionVideo) {
        PromotionVideo = promotionVideo;
    }

    public boolean isSendNotification() {
        return SendNotification;
    }

    public void setSendNotification(boolean sendNotification) {
        SendNotification = sendNotification;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public String getEndOfEffectiveDate() {
        return EndOfEffectiveDate;
    }

    public void setEndOfEffectiveDate(String endOfEffectiveDate) {
        EndOfEffectiveDate = endOfEffectiveDate;
    }

    public String getNotificationDate() {
        return NotificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        NotificationDate = notificationDate;
    }

    public int getNormalSellingPrice() {
        return NormalSellingPrice;
    }

    public void setNormalSellingPrice(int normalSellingPrice) {
        NormalSellingPrice = normalSellingPrice;
    }

    public int getPromotionSellingPrice() {
        return PromotionSellingPrice;
    }

    public void setPromotionSellingPrice(int promotionSellingPrice) {
        PromotionSellingPrice = promotionSellingPrice;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getMethodId() {
        return MethodId;
    }

    public void setMethodId(int methodId) {
        MethodId = methodId;
    }

    public String getMethodDescription() {
        return MethodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        MethodDescription = methodDescription;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}
