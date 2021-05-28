package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GridChildGrowthHistoryObject {

    @SerializedName("ColumnHeaderList")
    private ArrayList<ColumnHeaderChildGrowthObject> columnHeaderList;
    private ArrayList<String> columnHeaderListTemp;
    @SerializedName("RowHeaderList")
    private ArrayList<RowHeaderChildGrowthObject> rowHeaderList;
    @SerializedName("RowDataList")
    private ArrayList<GridMatrixDataListChildGrowthObject> rowDataList;

    public ArrayList<ColumnHeaderChildGrowthObject> getColumnHeaderList() {
        return columnHeaderList;
    }

    public void setColumnHeaderList(ArrayList<ColumnHeaderChildGrowthObject> columnHeaderList) {
        this.columnHeaderList = columnHeaderList;
    }

    public ArrayList<String> getColumnHeaderListTemp() {
        return columnHeaderListTemp;
    }

    public void setColumnHeaderListTemp(ArrayList<String> columnHeaderListTemp) {
        this.columnHeaderListTemp = columnHeaderListTemp;
    }

    public ArrayList<RowHeaderChildGrowthObject> getRowHeaderList() {
        return rowHeaderList;
    }

    public void setRowHeaderList(ArrayList<RowHeaderChildGrowthObject> rowHeaderList) {
        this.rowHeaderList = rowHeaderList;
    }

    public ArrayList<GridMatrixDataListChildGrowthObject> getRowDataList() {
        return rowDataList;
    }

    public void setRowDataList(ArrayList<GridMatrixDataListChildGrowthObject> rowDataList) {
        this.rowDataList = rowDataList;
    }
}
