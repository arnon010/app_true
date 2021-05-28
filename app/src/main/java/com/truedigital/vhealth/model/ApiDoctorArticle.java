package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ApiDoctorArticle extends NormalResponseObject {

    @SerializedName("Article") private ItemArticleDao article;
    @SerializedName("ArticleLangs") private List<ItemArticleLangsDao> articleLangs;
    @SerializedName("ArticleTags") private List<ItemArticleTagsDao> articleTags;

    public ItemArticleDao getArticle() {
        return article;
    }

    public void setArticle(ItemArticleDao article) {
        this.article = article;
    }

    public List<ItemArticleLangsDao> getArticleLangs() {
        return articleLangs;
    }

    public void setArticleLangs(List<ItemArticleLangsDao> articleLangs) {
        this.articleLangs = articleLangs;
    }

    public List<ItemArticleTagsDao> getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(List<ItemArticleTagsDao> articleTags) {
        this.articleTags = articleTags;
    }
}
