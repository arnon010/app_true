package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemMedicineDao extends NormalResponseObject {
    /*
    {
        "ProductId": 1,
            "MethodId": 1,
            "MethodDescription": "กินหลังอาหารสามมื้อ",
            "ProductName": "ยาดี",
            "Quantity": 10
    }
    */

    @SerializedName("ProductId") private int ProductId;
    @SerializedName("MethodId") private int MethodId;
    @SerializedName("MethodDescription") private String MethodDescription;
    @SerializedName("ProductName") private String ProductName;
    @SerializedName("Quantity") private int Quantity;

    @SerializedName("Unit") private String unit;
    @SerializedName("PromotionSellingPrice") private int PromotionSellingPrice;
    @SerializedName("NormalSellingPrice") private int NormalSellingPrice;

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
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPromotionSellingPrice() {
        return PromotionSellingPrice;
    }

    public void setPromotionSellingPrice(int promotionSellingPrice) {
        PromotionSellingPrice = promotionSellingPrice;
    }

    public int getNormalSellingPrice() {
        return NormalSellingPrice;
    }

    public void setNormalSellingPrice(int normalSellingPrice) {
        NormalSellingPrice = normalSellingPrice;
    }
}
