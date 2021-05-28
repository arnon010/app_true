package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiListCreditCard extends NormalResponseObject{

    @SerializedName("Data") private List<ItemListCreditCardDao> listData;

    public List<ItemListCreditCardDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemListCreditCardDao> listData) {
        this.listData = listData;
    }
}
