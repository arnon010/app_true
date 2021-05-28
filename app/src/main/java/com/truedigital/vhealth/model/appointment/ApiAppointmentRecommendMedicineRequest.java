package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

public class ApiAppointmentRecommendMedicineRequest extends NormalResponseObject {

    @SerializedName("Items") private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
