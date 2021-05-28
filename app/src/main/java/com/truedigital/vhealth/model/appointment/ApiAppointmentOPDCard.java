package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

public class ApiAppointmentOPDCard extends NormalResponseObject {

    @SerializedName("Data") private ItemAppointmentOPDCardDao data;

    public ItemAppointmentOPDCardDao getData() {
        return data;
    }

    public void setData(ItemAppointmentOPDCardDao data) {
        this.data = data;
    }
}
