package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ColumnHeaderChildGrowthObject {

    @SerializedName("ChildGrowthCategoryId")
    private int childGrowthCategoryId;
    @SerializedName("CategoryName")
    private String categoryName;

    public int getChildGrowthCategoryId() {
        return childGrowthCategoryId;
    }

    public void setChildGrowthCategoryId(int childGrowthCategoryId) {
        this.childGrowthCategoryId = childGrowthCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
