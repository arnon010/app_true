package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiListBankAccountObject extends NormalResponseObject{

    @SerializedName("Data")
    private ArrayList<BankAccountObject> dataList;

    public ArrayList<BankAccountObject> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<BankAccountObject> dataList) {
        this.dataList = dataList;
    }

}