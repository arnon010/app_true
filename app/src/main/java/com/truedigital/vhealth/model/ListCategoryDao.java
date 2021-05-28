package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListCategoryDao extends NormalResponseObject{

    @SerializedName("Data") private List<ItemCategoryDao> listData;

    public List<ItemCategoryDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemCategoryDao> listData) {
        this.listData = listData;
    }
}
