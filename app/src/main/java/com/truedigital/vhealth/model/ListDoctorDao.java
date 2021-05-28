package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListDoctorDao extends NormalResponseObject {

    @SerializedName("Data") private List<ItemDoctorDao> listData;

    public List<ItemDoctorDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemDoctorDao> listData) {
        this.listData = listData;
    }

}
