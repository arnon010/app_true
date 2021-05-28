package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;


public class ApiDoctorArticleRequest extends NormalResponseObject {

    @SerializedName("Article") private ApiDoctorArticle Article;

    public ApiDoctorArticle getArticle() {
        return Article;
    }

    public void setArticle(ApiDoctorArticle article) {
        Article = article;
    }
}
