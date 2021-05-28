package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListItemTimeSlotDao {

    @SerializedName("Data") private List<ItemTimeSlotDao> listData;

    public List<ItemTimeSlotDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemTimeSlotDao> listData) {
        this.listData = listData;
    }

    public String getListOpenSlot() {
        String buffer = "";
        for(ItemTimeSlotDao data :listData) {
            if (data.isOpen()) {
                if (buffer.isEmpty()) {
                    buffer = data.getTimes();
                }
                else {
                    buffer = buffer + ", " + data.getTimes();
                }
            }
        }
        return buffer;
    }
}
