package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ApiDoctorArticleGroup extends NormalResponseObject {

    @SerializedName("Data") private List<ItemArticleGroupDao> listdata;

    public List<ItemArticleGroupDao> getListdata() {
        return listdata;
    }

    public void setListdata(List<ItemArticleGroupDao> listdata) {
        this.listdata = listdata;
    }
}
