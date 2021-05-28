package com.truedigital.vhealth.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class FoodAllergyImageObject extends ImageObject implements Cloneable  {

    @SerializedName("Id")
    private int foodAllergyAttachmentId;
    @SerializedName("FoodAllergyId")
    private int foodAllergyId;
    @SerializedName("FileName")
    private String fileName;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("IsVideo")
    private boolean isVideo;

    private Uri imageUri;

    public int getFoodAllergyAttachmentId() { return foodAllergyAttachmentId;  }

    public void setFoodAllergyAttachmentId(int foodAllergyAttachmentId) { this.foodAllergyAttachmentId = foodAllergyAttachmentId; }

    public int getFoodAllergyId() { return foodAllergyId;  }

    public void setFoodAllergyId(int foodAllergyId) { this.foodAllergyId = foodAllergyId; }

    public String getFileName() { return fileName;  }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getImageUrl() { return imageUrl;  }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getIsVideo() { return isVideo;  }

    public void setIsVideo(Boolean isVideo) { this.isVideo = isVideo; }

    public Uri getImageUri() { return imageUri;  }

    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }

    public FoodAllergyImageObject clone() {
        try {
            return (FoodAllergyImageObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
