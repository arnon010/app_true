package com.truedigital.vhealth.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import kotlinx.parcelize.Parcelize;

@Parcelize
public class ItemClinicDao extends NormalResponseObject implements Parcelable {

    /*
                "Id": 5859,
                        "Title": "สไมล์ ซิกเนเจอร์",
                        "Description": null,
                        "LogoImage": "https://dev-api.chiiwiilive.com/WebService_2.0.0/api/attachments/Clinics/5859/th-TH",
                        "LogoImageWithoutCaption": "https://dev-api.chiiwiilive.com/WebService_2.0.0/api/attachments/Clinics/5859/nocaption",
                        "ClinicId": 0,
                        "Active": true,
                        "IsShow": true,
                        "clinicsubcategoryid": 0,
                        "ClinicLangs": null,
                        "ClinicSubCategoryLang": null,
                        "AllowDoctors": null,
                        "NB_OrderSeq": 0,
                        "IsShowText": "แสดง",
                        "ActiveText": "ใช่"
                        */

    @SerializedName("Id")
    private int id;
    @SerializedName("Title")
    private String title;
    @SerializedName("Description")
    private String description;
    @SerializedName("LogoImage")
    private String logoImage;
    @SerializedName("LogoImageWithoutCaption")
    private String logoImageWithoutCaption;
    @SerializedName("ClinicId")
    private int clinicId;
    @SerializedName("Active")
    private boolean active;
    @SerializedName("IsShow")
    private boolean show;

    protected ItemClinicDao(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        logoImage = in.readString();
        logoImageWithoutCaption = in.readString();
        clinicId = in.readInt();
        active = in.readByte() != 0;
        show = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(logoImage);
        dest.writeString(logoImageWithoutCaption);
        dest.writeInt(clinicId);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeByte((byte) (show ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemClinicDao> CREATOR = new Creator<ItemClinicDao>() {
        @Override
        public ItemClinicDao createFromParcel(Parcel in) {
            return new ItemClinicDao(in);
        }

        @Override
        public ItemClinicDao[] newArray(int size) {
            return new ItemClinicDao[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
