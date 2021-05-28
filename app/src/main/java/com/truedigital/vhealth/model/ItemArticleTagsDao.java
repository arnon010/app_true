package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemArticleTagsDao extends NormalResponseObject {

    @SerializedName("Tag") private String Tag;

    public ItemArticleTagsDao(String tag) {
        Tag = tag;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
