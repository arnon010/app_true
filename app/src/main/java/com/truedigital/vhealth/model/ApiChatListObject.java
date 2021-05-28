package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ApiChatListObject extends NormalResponseObject{

    @SerializedName("Data") private List<ApiChatObject> listData;

    public List<ApiChatObject> getListData() {
        return listData;
    }

    public void setListData(List<ApiChatObject> listData) {
        this.listData = listData;
    }
}
