package com.truedigital.vhealth.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class LaboratoryOtherImageObject extends ImageObject implements Cloneable  {

    @SerializedName("LabAttachmentId")
    private int labAttachmentId;
    @SerializedName("LabId")
    private int labId;
    @SerializedName("FileName")
    private String fileName;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("IsVideo")
    private boolean isVideo;

    private Uri imageUri;

    public int getLabAttachmentId() { return labAttachmentId;  }

    public void setLabAttachmentId(int labAttachmentId) { this.labAttachmentId = labAttachmentId; }

    public int getLabId() { return labId;  }

    public void setLabId(int labId) { this.labId = labId; }

    public String getFileName() { return fileName;  }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getImageUrl() { return imageUrl;  }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getIsVideo() { return isVideo;  }

    public void setIsVideo(Boolean isVideo) { this.isVideo = isVideo; }

    public Uri getImageUri() { return imageUri;  }

    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }

    public LaboratoryOtherImageObject clone() {
        try {
            return (LaboratoryOtherImageObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
