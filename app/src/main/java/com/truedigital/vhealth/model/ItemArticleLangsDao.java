package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemArticleLangsDao extends NormalResponseObject {

    @SerializedName("ArticleId") private int ArticleId;
    @SerializedName("LanguageCode") private String LanguageCode;
    @SerializedName("Title") private String Title;
    @SerializedName("ShortDescription") private String ShortDescription;
    @SerializedName("Description") private String Description;

    public ItemArticleLangsDao(String languageCode, String title, String shortDescription, String description) {
        LanguageCode = languageCode;
        Title = title;
        ShortDescription = shortDescription;
        Description = description;
    }

    public int getArticleId() {
        return ArticleId;
    }

    public void setArticleId(int articleId) {
        ArticleId = articleId;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
