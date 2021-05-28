package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListSubCategoryDao extends NormalResponseObject{

    @SerializedName("Data") private List<ItemSubCategoryDao> listData;

    public List<ItemSubCategoryDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemSubCategoryDao> listData) {
        this.listData = listData;
    }
}
