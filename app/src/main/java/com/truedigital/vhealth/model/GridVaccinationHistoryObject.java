package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GridVaccinationHistoryObject {

    @SerializedName("ColumnHeaderList")
    private ArrayList<ColumnHeaderVaccineObject> columnHeaderList;
    private ArrayList<String> columnHeaderListTemp;
    @SerializedName("RowHeaderList")
    private ArrayList<RowHeaderVaccineObject> rowHeaderList;
    @SerializedName("RowDataList")
    private ArrayList<GridMatrixDataListVaccineObject> rowDataList;

    public ArrayList<ColumnHeaderVaccineObject> getColumnHeaderList() {
        return columnHeaderList;
    }

    public void setColumnHeaderList(ArrayList<ColumnHeaderVaccineObject> columnHeaderList) {
        this.columnHeaderList = columnHeaderList;
    }

    public ArrayList<String> getColumnHeaderListTemp() {
        return columnHeaderListTemp;
    }

    public void setColumnHeaderListTemp(ArrayList<String> columnHeaderListTemp) {
        this.columnHeaderListTemp = columnHeaderListTemp;
    }

    public ArrayList<RowHeaderVaccineObject> getRowHeaderList() {
        return rowHeaderList;
    }

    public void setRowHeaderList(ArrayList<RowHeaderVaccineObject> rowHeaderList) {
        this.rowHeaderList = rowHeaderList;
    }

    public ArrayList<GridMatrixDataListVaccineObject> getRowDataList() {
        return rowDataList;
    }

    public void setRowDataList(ArrayList<GridMatrixDataListVaccineObject> rowDataList) {
        this.rowDataList = rowDataList;
    }

}
