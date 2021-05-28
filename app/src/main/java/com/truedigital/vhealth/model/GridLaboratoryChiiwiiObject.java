package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GridLaboratoryChiiwiiObject {

    @SerializedName("ColumnHeaderList")
    private ArrayList<ColumnHeaderChiiwiiLabObject> columnHeaderList;
    private ArrayList<String> columnHeaderListTemp;
    @SerializedName("RowHeaderList")
    private ArrayList<RowHeaderChiiwiiLabObject> rowHeaderList;
    @SerializedName("RowDataList")
    private ArrayList<GridMatrixDataListChiiwiiLabObject> rowDataList;

    public ArrayList<ColumnHeaderChiiwiiLabObject> getColumnHeaderList() {
        return columnHeaderList;
    }

    public void setColumnHeaderList(ArrayList<ColumnHeaderChiiwiiLabObject> columnHeaderList) {
        this.columnHeaderList = columnHeaderList;
    }

    public ArrayList<String> getColumnHeaderListTemp() {
        return columnHeaderListTemp;
    }

    public void setColumnHeaderListTemp(ArrayList<String> columnHeaderListTemp) {
        this.columnHeaderListTemp = columnHeaderListTemp;
    }

    public ArrayList<RowHeaderChiiwiiLabObject> getRowHeaderList() {
        return rowHeaderList;
    }

    public void setRowHeaderList(ArrayList<RowHeaderChiiwiiLabObject> rowHeaderList) {
        this.rowHeaderList = rowHeaderList;
    }

    public ArrayList<GridMatrixDataListChiiwiiLabObject> getRowDataList() {
        return rowDataList;
    }

    public void setRowDataList(ArrayList<GridMatrixDataListChiiwiiLabObject> rowDataList) {
        this.rowDataList = rowDataList;
    }

}
