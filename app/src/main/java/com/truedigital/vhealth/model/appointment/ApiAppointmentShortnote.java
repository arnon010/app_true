package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

public class ApiAppointmentShortnote extends NormalResponseObject {

    @SerializedName("Data") private ItemAppointmentShortnoteDao data;

    public ItemAppointmentShortnoteDao getData() {
        return data;
    }

    public void setData(ItemAppointmentShortnoteDao data) {
        this.data = data;
    }
}
