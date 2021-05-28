package com.truedigital.vhealth.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class MedicationHistoryImageObject  extends ImageObject implements Cloneable  {

    @SerializedName("MedicinationAttachmentId")
    private int medicinationAttachmentId;
    @SerializedName("MedicinationId")
    private int medicinationId;
    @SerializedName("FileName")
    private String fileName;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("IsVideo")
    private boolean isVideo;

    private Uri imageUri;

    public int getMedicinationAttachmentId() { return medicinationAttachmentId;  }

    public void setMedicinationAttachmentId(int medicinationAttachmentId) { this.medicinationAttachmentId = medicinationAttachmentId; }

    public int getMedicinationId() { return medicinationId;  }

    public void setMedicinationId(int medicinationId) { this.medicinationId = medicinationId; }

    public String getFileName() { return fileName;  }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getImageUrl() { return imageUrl;  }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getIsVideo() { return isVideo;  }

    public void setIsVideo(Boolean isVideo) { this.isVideo = isVideo; }

    public Uri getImageUri() { return imageUri;  }

    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }

    public MedicationHistoryImageObject clone() {
        try {
            return (MedicationHistoryImageObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
