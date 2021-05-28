package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiAppointmentRecommendProduct extends NormalResponseObject {

    @SerializedName("Data") private List<ItemProductDao> data;

    public List<ItemProductDao> getData() {
        return data;
    }

    public void setData(List<ItemProductDao> data) {
        this.data = data;
    }
}
