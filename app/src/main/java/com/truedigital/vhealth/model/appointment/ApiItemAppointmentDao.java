package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

public class ApiItemAppointmentDao extends NormalResponseObject {

    @SerializedName("Data") private ItemAppointmentDao data;

    public ItemAppointmentDao getData() {
        return data;
    }

    public void setData(ItemAppointmentDao data) {
        this.data = data;
    }
}