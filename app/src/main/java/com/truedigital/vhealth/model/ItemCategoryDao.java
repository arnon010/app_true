package com.truedigital.vhealth.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import kotlinx.parcelize.Parcelize;

@Parcelize
public class ItemCategoryDao implements Parcelable {

    @SerializedName("Id") private int id;
    @SerializedName("Description") private String description;
    @SerializedName("LogoImage") private String logoImage;
    @SerializedName("LogoImageWithoutCaption") private String logoImageWithoutCaption;
    @SerializedName("IsShow") private boolean show;
    @SerializedName("Active") private boolean active;

    protected ItemCategoryDao(Parcel in) {
        id = in.readInt();
        description = in.readString();
        logoImage = in.readString();
        logoImageWithoutCaption = in.readString();
        show = in.readByte() != 0;
        active = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(logoImage);
        dest.writeString(logoImageWithoutCaption);
        dest.writeByte((byte) (show ? 1 : 0));
        dest.writeByte((byte) (active ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemCategoryDao> CREATOR = new Creator<ItemCategoryDao>() {
        @Override
        public ItemCategoryDao createFromParcel(Parcel in) {
            return new ItemCategoryDao(in);
        }

        @Override
        public ItemCategoryDao[] newArray(int size) {
            return new ItemCategoryDao[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getLogoImageWithoutCaption() {
        return logoImageWithoutCaption;
    }

    public void setLogoImageWithoutCaption(String logoImageWithoutCaption) {
        this.logoImageWithoutCaption = logoImageWithoutCaption;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
