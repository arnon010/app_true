package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListProductGroupDao extends NormalResponseObject {

    @SerializedName("Data") private List<ItemProductGroupDao> listData;

    public List<ItemProductGroupDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemProductGroupDao> listData) {
        this.listData = listData;
    }
}
