package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RecommendMedicationObject {



    @SerializedName("RecommendId")
    private int recommendId;
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("Status")
    private String status;
    @SerializedName("PromotionType")
    private int promotionType;
    @SerializedName("ItemId")
    private int itemId;
    @SerializedName("NormalSellingPrice")
    private float normalSellingPrice;
    @SerializedName("PromotionSellingPrice")
    private float promotionSellingPrice;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("ProductId")
    private int productId;
    @SerializedName("EndOfEffectiveDate")
    private String endOfEffectiveDateString;
    private Date endOfEffectiveDate;
    @SerializedName("Unit")
    private String unit;
    @SerializedName("Title")
    private String title;
    @SerializedName("ShortDescription")
    private String shortDescription;
    @SerializedName("Description")
    private String description;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("HowToUse")
    private String howToUse;
    @SerializedName("NormalSellingPriceVat")
    private float normalSellingPriceVat;
    @SerializedName("PromotionSellingPriceVat")
    private float promotionSellingPriceVat;
    @SerializedName("NormalSellingPriceNoVat")
    private float normalSellingPriceNoVat;
    @SerializedName("PromotionSellingPriceNoVat")
    private float promotionSellingPriceNoVat;


    public int getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(int recommendId) {
        this.recommendId = recommendId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public float getNormalSellingPrice() {
        return normalSellingPrice;
    }

    public void setNormalSellingPrice(float normalSellingPrice) {
        this.normalSellingPrice = normalSellingPrice;
    }

    public float getPromotionSellingPrice() {
        return promotionSellingPrice;
    }

    public void setPromotionSellingPrice(float promotionSellingPrice) {
        this.promotionSellingPrice = promotionSellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getEndOfEffectiveDateString() {
        return endOfEffectiveDateString;
    }

    public void setEndOfEffectiveDateString(String endOfEffectiveDateString) {
        this.endOfEffectiveDateString = endOfEffectiveDateString;
    }

    public Date getEndOfEffectiveDate() {
        return endOfEffectiveDate;
    }

    public void setEndOfEffectiveDate(Date endOfEffectiveDate) {
        this.endOfEffectiveDate = endOfEffectiveDate;
        if(endOfEffectiveDate != null)
        {
            this.endOfEffectiveDateString = ConvertDate.convertDateToServiceFormat(endOfEffectiveDate);
        }
        else
        {
            this.endOfEffectiveDateString = null;
        }
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public float getNormalSellingPriceVat() {
        return normalSellingPriceVat;
    }

    public void setNormalSellingPriceVat(float normalSellingPriceVat) {
        this.normalSellingPriceVat = normalSellingPriceVat;
    }

    public float getPromotionSellingPriceVat() {
        return promotionSellingPriceVat;
    }

    public void setPromotionSellingPriceVat(float promotionSellingPriceVat) {
        this.promotionSellingPriceVat = promotionSellingPriceVat;
    }

    public float getNormalSellingPriceNoVat() {
        return normalSellingPriceNoVat;
    }

    public void setNormalSellingPriceNoVat(float normalSellingPriceNoVat) {
        this.normalSellingPriceNoVat = normalSellingPriceNoVat;
    }

    public float getPromotionSellingPriceNoVat() {
        return promotionSellingPriceNoVat;
    }

    public void setPromotionSellingPriceNoVat(float promotionSellingPriceNoVat) {
        this.promotionSellingPriceNoVat = promotionSellingPriceNoVat;
    }


}
