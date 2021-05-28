package com.truedigital.vhealth.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ItemPersonDao implements Parcelable{

    @SerializedName("Id") private int id;
    @SerializedName("Name") private String name;
    @SerializedName("ProfileImage") private String profileImage;

    protected ItemPersonDao(Parcel in) {
        id = in.readInt();
        name = in.readString();
        profileImage = in.readString();
    }

    public static final Creator<ItemPersonDao> CREATOR = new Creator<ItemPersonDao>() {
        @Override
        public ItemPersonDao createFromParcel(Parcel in) {
            return new ItemPersonDao(in);
        }

        @Override
        public ItemPersonDao[] newArray(int size) {
            return new ItemPersonDao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(profileImage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
