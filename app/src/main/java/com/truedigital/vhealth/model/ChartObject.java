package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChartObject {

    @SerializedName("ShowDate")
    private String showDate;
    @SerializedName("XLabel")
    private ArrayList<String> xLabel;
    @SerializedName("DataList")
    private ArrayList<ChartDataObject> dataList;

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public ArrayList<String> getXLabel() {
        return xLabel;
    }

    public void setXLabel(ArrayList<String> xLabel) {
        this.xLabel = xLabel;
    }

    public ArrayList<ChartDataObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<ChartDataObject> dataList) {
        this.dataList = dataList;
    }


}
