package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListProductBannerDao extends NormalResponseObject {

    @SerializedName("Data") private List<ItemProductBannerDao> listData;

    public List<ItemProductBannerDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemProductBannerDao> listData) {
        this.listData = listData;
    }
}
