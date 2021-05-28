package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiGridLaboratoryChiiwiiObject extends NormalResponseObject {

    @SerializedName("Data")
    private GridLaboratoryChiiwiiObject data;

    public GridLaboratoryChiiwiiObject getData() {
        return data;
    }

    public void setData(GridLaboratoryChiiwiiObject data) {
        this.data = data;
    }
}
