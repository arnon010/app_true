package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ApiListDoctorAvailableTimes extends NormalResponseObject{

    @SerializedName("Data")
    private List<ItemDoctorScheduleTimeDao> listData;

    public List<ItemDoctorScheduleTimeDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemDoctorScheduleTimeDao> listData) {
        this.listData = listData;
    }
}
