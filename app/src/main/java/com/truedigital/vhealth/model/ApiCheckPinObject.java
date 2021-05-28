package com.truedigital.vhealth.model;
import com.google.gson.annotations.SerializedName;

public class ApiCheckPinObject extends NormalResponseObject {
    @SerializedName("HasPIN")
    private boolean hasPIN;

    public boolean getHasPIN() {
        return hasPIN;
    }

    public void setHasPIN(boolean hasPin) {
        this.hasPIN = hasPin;
    }
}
