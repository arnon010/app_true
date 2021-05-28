package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ShortNoteAttachmentObject extends ImageObject {

    @SerializedName("Id")
    private int id;
    @SerializedName("Url")
    private String imageUrl;
    @SerializedName("FileName")
    private String fileName;

    public int getId() { return id;  }

    public void setId(int id) { this.id = id; }

    public String getImageUrl() { return imageUrl;  }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getFileName() { return fileName;  }

    public void setFileName(String fileName) { this.fileName = fileName; }

}
