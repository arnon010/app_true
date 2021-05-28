package com.truedigital.vhealth.model.discount;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by songkrit on 10/11/2018 AD.
 */

public class ItemDiscount extends NormalResponseObject {

    @SerializedName("DiscountPrice") private double discountPrice;

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}
