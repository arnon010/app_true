package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class RowHeaderChiiwiiLabObject extends GridMatrixRowHeaderObject {

    @SerializedName("FieldId")
    private int fieldId;
    @SerializedName("Name")
    private String name;

    public int getFieldId() { return fieldId;  }

    public void setFieldId(int fieldId) {  this.fieldId = fieldId; }

    public String getName() { return name;  }

    public void setName(String name) {
        this.name = name;
        this.setText(name);
    }

}
