package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiRecommendProductCouponsObject extends NormalResponseObject{

    @SerializedName("Data")
    private float discountPrice;

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

}