package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiEhrMenuObject extends NormalResponseObject{

    @SerializedName("Data")
    private EhrMenuObject menu;

    public EhrMenuObject getMenu() {
        return menu;
    }

    public void setMenu(EhrMenuObject menu) {
        this.menu = menu;
    }

}
