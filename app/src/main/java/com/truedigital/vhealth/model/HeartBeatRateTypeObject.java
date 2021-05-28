package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class HeartBeatRateTypeObject extends BottomSheetObject {

    @SerializedName("Value")
    private String valueItem;
    @SerializedName("Descriptions")
    private String descriptions;

    public String getDescriptions() { return descriptions;  }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getValueItem() { return valueItem;  }

    public void setValueItem(String valueItem) { this.valueItem = valueItem; }

}
