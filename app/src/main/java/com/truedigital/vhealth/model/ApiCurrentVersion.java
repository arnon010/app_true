package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songkrit on 8/24/2017 AD.
 */

public class ApiCurrentVersion extends NormalResponseObject {
    //@SerializedName("LastestBuildNumber") private int LastestVersion;
    //@SerializedName("MinimumBuildNumber") private int MinimumSdk;
    @SerializedName("last") private int last;
    @SerializedName("min") private int min;

    /*
    public int getLastestVersion() {
        return LastestVersion;
    }

    public void setLastestVersion(int lastestVersion) {
        LastestVersion = lastestVersion;
    }

    public int getMinimumSdk() {
        return MinimumSdk;
    }

    public void setMinimumSdk(int minimumSdk) {
        MinimumSdk = minimumSdk;
    }

    */

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
