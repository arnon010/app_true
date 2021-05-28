package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListPersonDao extends NormalResponseObject {

    @SerializedName("Data") private List<ItemPersonDao> listData;

    public List<ItemPersonDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemPersonDao> listData) {
        this.listData = listData;
    }
}
