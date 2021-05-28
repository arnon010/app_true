package com.truedigital.vhealth.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class PregnantHistoryImageObject extends ImageObject implements Cloneable {

    @SerializedName("PregnancyAttachmentId")
    private int pregnancyAttachmentId;
    @SerializedName("PregnancyId")
    private int pregnancyId;
    @SerializedName("FileName")
    private String fileName;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("IsVideo")
    private boolean isVideo;

    private Uri imageUri;

    public int getPregnancyAttachmentId() { return pregnancyAttachmentId;  }

    public void setPregnancyAttachmentId(int pregnancyAttachmentId) { this.pregnancyAttachmentId = pregnancyAttachmentId; }

    public int getPregnancyId() { return pregnancyId;  }

    public void setPregnancyId(int pregnancyId) { this.pregnancyId = pregnancyId; }

    public String getFileName() { return fileName;  }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getImageUrl() { return imageUrl;  }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getIsVideo() { return isVideo;  }

    public void setIsVideo(Boolean isVideo) { this.isVideo = isVideo; }

    public Uri getImageUri() { return imageUri;  }

    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }

    public PregnantHistoryImageObject clone() {
        try {
            return (PregnantHistoryImageObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
