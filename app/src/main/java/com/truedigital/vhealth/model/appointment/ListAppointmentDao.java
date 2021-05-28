package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListAppointmentDao extends NormalResponseObject {

    @SerializedName("Data")
    private List<ItemAppointmentDao> listData;

    public List<ItemAppointmentDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemAppointmentDao> listData) {
        this.listData = listData;
    }
}
