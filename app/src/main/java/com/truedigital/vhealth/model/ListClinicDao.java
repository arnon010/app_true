package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListClinicDao extends NormalResponseObject{

    @SerializedName("Data") private List<ItemClinicDao> listData;

    public List<ItemClinicDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemClinicDao> listData) {
        this.listData = listData;
    }
}
