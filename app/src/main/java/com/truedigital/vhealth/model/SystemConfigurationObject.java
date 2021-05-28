package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class SystemConfigurationObject {

    @SerializedName("ImageMaxSizeByte")
    private long imageMaxSizeByte;
    @SerializedName("VideoMaxSizeByte")
    private long videoMaxSizeByte;

    public long getImageMaxSizeByte() {
        return imageMaxSizeByte;
    }

    public void setImageMaxSizeByte(long imageMaxSizeByte) {
        this.imageMaxSizeByte = imageMaxSizeByte;
    }

    public long getVideoMaxSizeByte() {
        return videoMaxSizeByte;
    }

    public void setVideoMaxSizeByte(long videoMaxSizeByte) {
        this.videoMaxSizeByte = videoMaxSizeByte;
    }


}
