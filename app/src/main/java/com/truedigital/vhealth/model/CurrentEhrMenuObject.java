package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentEhrMenuObject {

    @SerializedName("MenuList")
    private ArrayList<EhrMenuObject> menuList;
    @SerializedName("CanAddNewMenu")
    private boolean canAddNewMenu;


    public ArrayList<EhrMenuObject> getMenuList() { return menuList;  }

    public void setMenuList(ArrayList<EhrMenuObject> menuList) {
        this.menuList = menuList;
    }

    public boolean getCanAddNewMenu() { return canAddNewMenu;  }

    public void setCanAddNewMenu(boolean canAddNewMenu) {
        this.canAddNewMenu = canAddNewMenu;
    }

}
