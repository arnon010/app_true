package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class RowHeaderChildGrowthObject extends GridMatrixRowHeaderObject {

    @SerializedName("ChildGrowthId")
    private int childGrowthId;
    @SerializedName("MonthDisplay")
    private String monthDisplay;
    @SerializedName("Month")
    private int month;
    @SerializedName("CategoryId")
    private int categoryId;

    public int getChildGrowthId() { return childGrowthId;  }

    public void setChildGrowthId(int childGrowthId) {  this.childGrowthId = childGrowthId; }

    public String getMonthDisplay() { return monthDisplay;  }

    public void setMonthDisplay(String monthDisplay) {  this.monthDisplay = monthDisplay; }

    public int getMonth() { return month;  }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getCategoryId() { return categoryId;  }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
