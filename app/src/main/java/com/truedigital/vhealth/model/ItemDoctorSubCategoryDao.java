package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorSubCategoryDao {
    /*
      "CategoryId": 0,
      "SubCategoryId": 7518,
      "CategoryName": "พัฒนาการ"
    */

    @SerializedName("CategoryId") private int categoryId;
    @SerializedName("SubCategoryId") private int subCategoryId;
    @SerializedName("CategoryName") private String categoryName;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
