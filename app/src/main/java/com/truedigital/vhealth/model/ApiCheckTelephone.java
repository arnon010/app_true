package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songkrit on 2/6/2017 AD.
 */

public class ApiCheckTelephone extends NormalResponseObject{

    @SerializedName("HasAccount") private boolean HasAccount;

    public ApiCheckTelephone() {
    }

    public boolean isHasAccount() {
        return HasAccount;
    }

    public void setHasAccount(boolean hasAccount) {
        HasAccount = hasAccount;
    }
}
