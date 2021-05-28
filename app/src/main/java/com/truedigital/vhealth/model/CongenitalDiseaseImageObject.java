package com.truedigital.vhealth.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class CongenitalDiseaseImageObject extends ImageObject implements Cloneable  {

    @SerializedName("Id")
    private int congenitalDiseaseAttachmentId;
    @SerializedName("CongenitalDiseaseId")
    private int congenitalDiseaseId;
    @SerializedName("FileName")
    private String fileName;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("IsVideo")
    private boolean isVideo;

    private Uri imageUri;

    public int getCongenitalDiseaseAttachmentId() { return congenitalDiseaseAttachmentId;  }

    public void setCongenitalDiseaseAttachmentId(int congenitalDiseaseAttachmentId) { this.congenitalDiseaseAttachmentId = congenitalDiseaseAttachmentId; }

    public int getCongenitalDiseaseId() { return congenitalDiseaseId;  }

    public void setCongenitalDiseaseId(int congenitalDiseaseId) { this.congenitalDiseaseId = congenitalDiseaseId; }

    public String getFileName() { return fileName;  }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getImageUrl() { return imageUrl;  }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getIsVideo() { return isVideo;  }

    public void setIsVideo(Boolean isVideo) { this.isVideo = isVideo; }

    public Uri getImageUri() { return imageUri;  }

    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }

    public CongenitalDiseaseImageObject clone() {
        try {
            return (CongenitalDiseaseImageObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
