package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChartDataObject {

    @SerializedName("AxisX")
    private int axisX;
    @SerializedName("Type")
    private String type;
    @SerializedName("ValueList")
    private ArrayList<ChartValueObject> valueList;

    public int getAxisX() {
        return axisX;
    }

    public void setAxisX(int axisX) {
        this.axisX = axisX;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ChartValueObject> getValueList() {
        return valueList;
    }

    public void setValueList(ArrayList<ChartValueObject> valueList) {
        this.valueList = valueList;
    }

}
