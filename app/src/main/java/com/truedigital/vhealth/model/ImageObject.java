package com.truedigital.vhealth.model;

public class ImageObject {
    private String fileName;
    private String imageUrl;
    private String imageUrlTemp;
    private Boolean isVideo;
    private Boolean isDelete;
    private Boolean isNew;
    private int percentage;
    private Boolean isDownload;
    private Boolean isDownloadComplete;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlTemp() {
        return imageUrlTemp;
    }

    public void setImageUrlTemp(String imageUrlTemp) {
        this.imageUrlTemp = imageUrlTemp;
    }

    public Boolean getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsNew() {
        if (isNew == null) {
            return false;
        }
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Boolean getIsDownload() {
        if (isDownload == null) {
            return false;
        }
        return isDownload;
    }

    public void setIsDownload(Boolean isDownload) {
        this.isDownload = isDownload;
    }

    public Boolean getIsDownloadComplete() {
        if (isDownloadComplete == null) {
            return false;
        }
        return isDownloadComplete;
    }

    public void setIsDownloadComplete(Boolean isDownloadComplete) {
        this.isDownloadComplete = isDownloadComplete;
    }

}
