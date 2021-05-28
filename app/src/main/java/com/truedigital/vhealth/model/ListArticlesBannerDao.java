package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListArticlesBannerDao extends NormalResponseObject {

    @SerializedName("Data") private List<ItemArticleDao> listData;

    public List<ItemArticleDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemArticleDao> listData) {
        this.listData = listData;
    }
}
