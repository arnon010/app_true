package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songkrit on 2/6/2017 AD.
 */

public class ApiGenerateOTP extends NormalResponseObject{

    @SerializedName("ReferenceCode")
    private String reference;
    @SerializedName("Message")
    private String message;

    public ApiGenerateOTP() {
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
