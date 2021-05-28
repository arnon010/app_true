package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListEhrMenuObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<EhrMenuObject> menuList;

    public ArrayList<EhrMenuObject> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<EhrMenuObject> menuList) {
        this.menuList = menuList;
    }

}
