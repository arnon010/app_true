package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;


public class DoctorFavoriteRequest extends NormalResponseObject {

    @SerializedName("IsFavorite") private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
