package com.truedigital.vhealth.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class MedicineAllergyImageObject extends ImageObject implements Cloneable  {

    @SerializedName("Id")
    private int medicineAllergyAttachmentId;
    @SerializedName("MedicineAllergyId")
    private int medicineAllergyId;
    @SerializedName("FileName")
    private String fileName;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("IsVideo")
    private boolean isVideo;

    private Uri imageUri;

    public int getMedicineAllergyAttachmentId() { return medicineAllergyAttachmentId;  }

    public void setMedicineAllergyAttachmentId(int medicineAllergyAttachmentId) { this.medicineAllergyAttachmentId = medicineAllergyAttachmentId; }

    public int getMedicineAllergyId() { return medicineAllergyId;  }

    public void setMedicineAllergyId(int medicineAllergyId) { this.medicineAllergyId = medicineAllergyId; }

    public String getFileName() { return fileName;  }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getImageUrl() { return imageUrl;  }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getIsVideo() { return isVideo;  }

    public void setIsVideo(Boolean isVideo) { this.isVideo = isVideo; }

    public Uri getImageUri() { return imageUri;  }

    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }

    public MedicineAllergyImageObject clone() {
        try {
            return (MedicineAllergyImageObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
