package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.ItemMedicineDao;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiAppointmentRecommendMedicine extends NormalResponseObject {

    @SerializedName("Data") private List<ItemMedicineDao> data;

    public List<ItemMedicineDao> getData() {
        return data;
    }

    public void setData(List<ItemMedicineDao> data) {
        this.data = data;
    }
}
