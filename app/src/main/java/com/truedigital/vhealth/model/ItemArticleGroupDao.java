package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemArticleGroupDao extends NormalResponseObject {

    @SerializedName("Id") private int id;
    @SerializedName("GroupName") private String GroupName;
    @SerializedName("GroupImage") private String GroupImage;
    @SerializedName("Active") private boolean active;
    @SerializedName("OrderSeq") private int OrderSeq;
    @SerializedName("ActiveText") private String ActiveText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getOrderSeq() {
        return OrderSeq;
    }

    public void setOrderSeq(int orderSeq) {
        OrderSeq = orderSeq;
    }

    public String getActiveText() {
        return ActiveText;
    }

    public void setActiveText(String activeText) {
        ActiveText = activeText;
    }
}
