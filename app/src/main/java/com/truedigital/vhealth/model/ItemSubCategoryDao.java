package com.truedigital.vhealth.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import kotlinx.parcelize.Parcelize;

@Parcelize
public class ItemSubCategoryDao implements Parcelable {

    @SerializedName("Id") private int id;
    @SerializedName("Description") private String description;
    @SerializedName("LogoImageWithoutCaption") private String logoImageWithoutCaption;
    @SerializedName("LogoImage") private String logoImage;
    @SerializedName("IsShow") private boolean show;
    @SerializedName("Active") private boolean active;

    protected ItemSubCategoryDao(Parcel in) {
        id = in.readInt();
        description = in.readString();
        logoImageWithoutCaption = in.readString();
        logoImage = in.readString();
        show = in.readByte() != 0;
        active = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(logoImageWithoutCaption);
        dest.writeString(logoImage);
        dest.writeByte((byte) (show ? 1 : 0));
        dest.writeByte((byte) (active ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemSubCategoryDao> CREATOR = new Creator<ItemSubCategoryDao>() {
        @Override
        public ItemSubCategoryDao createFromParcel(Parcel in) {
            return new ItemSubCategoryDao(in);
        }

        @Override
        public ItemSubCategoryDao[] newArray(int size) {
            return new ItemSubCategoryDao[size];
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

    public String getLogoImageWithoutCaption() {
        return logoImageWithoutCaption;
    }

    public void setLogoImageWithoutCaption(String logoImageWithoutCaption) {
        this.logoImageWithoutCaption = logoImageWithoutCaption;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
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
