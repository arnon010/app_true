package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemProductGroupDao {

    @SerializedName("Id") private int id;
    @SerializedName("Active") private boolean active;
    @SerializedName("GroupName") private String GroupName;
    @SerializedName("GroupImage") private String GroupImage;
    @SerializedName("OrderSeq") private int OrderSeq;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupImage() {
        return GroupImage;
    }

    public void setGroupImage(String groupImage) {
        GroupImage = groupImage;
    }

    public int getOrderSeq() {
        return OrderSeq;
    }

    public void setOrderSeq(int orderSeq) {
        OrderSeq = orderSeq;
    }
}
