package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by warinthorn_s on 5/22/2018.
 */

public class EhrMenuObject extends ListItemObject {

    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("MenuCode")
    private String menuCode;
    @SerializedName("MenuName")
    private String menuName;
    @SerializedName("DisplayName")
    private String displayName;
    @SerializedName("LogoImage")
    private String logoImage;
    @SerializedName("CanHaveMultiple")
    private boolean canHaveMultiple;

    public int getPatientMenuId() { return patientMenuId;  }

    public void setPatientMenuId(int patientMenuId) {
        this.patientMenuId = patientMenuId;
    }

    public String getMenuCode() { return menuCode;  }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() { return menuName;  }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
        this.setText(menuName);
    }

    public String getDisplayName() { return displayName;  }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImageUrl() { return logoImage;  }

    public void setImageUrl(String imageUrl) {  this.logoImage = imageUrl; }

    public boolean getCanHaveMultiple() { return canHaveMultiple;  }

    public void setCanHaveMultiple(boolean canHaveMultiple) { this.canHaveMultiple = canHaveMultiple; }

}
