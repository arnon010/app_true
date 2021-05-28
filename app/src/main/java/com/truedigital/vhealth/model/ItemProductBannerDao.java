package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemProductBannerDao {

    @SerializedName("ProductId") private int productId;
    @SerializedName("BannerImage") private String bannerImage;
    @SerializedName("ShortDescription") private String shortDescription;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
