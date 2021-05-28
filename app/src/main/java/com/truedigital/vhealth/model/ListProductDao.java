package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListProductDao extends NormalResponseObject {

    @SerializedName("Data") private List<ItemProductDao> listData;

    public List<ItemProductDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemProductDao> listData) {
        this.listData = listData;
    }
}
