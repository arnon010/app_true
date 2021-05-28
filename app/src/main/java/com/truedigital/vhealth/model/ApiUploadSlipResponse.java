package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songkrit on 2/6/2017 AD.
 */

public class ApiUploadSlipResponse extends NormalResponseObject{

    @SerializedName("url") private String slipUrl;

    public String getSlipUrl() {
        return slipUrl;
    }

    public void setSlipUrl(String slipUrl) {
        this.slipUrl = slipUrl;
    }
}
