package com.truedigital.vhealth.model;

import java.util.ArrayList;

public class GridMatrixDataListObject<T> {

    private ArrayList<T> dataItemList;

    public ArrayList<T> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(ArrayList<T> dataItemList) {
        this.dataItemList = dataItemList;
    }
}

