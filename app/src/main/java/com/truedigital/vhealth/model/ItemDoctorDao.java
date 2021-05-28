package com.truedigital.vhealth.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kotlinx.parcelize.Parcelize;

@Parcelize
public class ItemDoctorDao extends NormalResponseObject implements Parcelable {

    /*
                "ClosestTime": "2018-10-25T09:00:00",
                "DoctorId": 16,
                "Email": "songkritv@gmail.com",
                "Name": "นาย นาย สมชาย .... ใจดี .....",
                "ProfileImage": "https://dev-api.chiiwiilive.com/WebService_2.0.0/api/attachments/Doctors/16",
                "Price": 300,
                "PriceWithFee": 360,
                "ContactPeriod": "00:15:00",
                "ContactSizeMinutes": 15,
                "SlotPeriod": "00:20:00",
                "SlotSizeMinutes": 20,
                "CurrencySymbol": "THB",
                "CategoryName": null,
                "UserId": "34",
                "Phone": "0988888888",
                "Address": "aad",

                "OnlyShowInClinic": false,
                "IsVerify": true,
                "Active": true,
                "IsStandByAXA": false,
                "IsStandByChiiwii": false,

                "CareerCertificationNumber": "12345678900000",
                "PricePerMinuteFormat": "THB 360/15 นาที",
                "SpecialityDescription": "children health .....",

                "SubCategoryName": [
                    "พัฒนาการ",
                    "การตั้งครรภ์/คุมกำเนิด",
                    "ออกกำลังกาย"
                ],

                "Attachments": null,
                "LanguageSkill": null,

                "DoctorSubCategories": [
                {
                    "CategoryId": 0,
                    "SubCategoryId": 7518,
                    "CategoryName": "พัฒนาการ"
                },
                {
                    "CategoryId": 0,
                    "SubCategoryId": 7598,
                    "CategoryName": "การตั้งครรภ์/คุมกำเนิด"
                },
                {
                    "CategoryId": 0,
                    "SubCategoryId": 7616,
                    "CategoryName": "ออกกำลังกาย"
                }
            ],
                "DoctorSubClinics": null,
                "WorkShift": null,
                "ClosestAvailableInMinute": 783,
                "IsSuccess": true,
                "ErrorMessages": null
    */

    @SerializedName("ClosestTime")
    private String ClosestTime;
    @SerializedName("DoctorId")
    private int doctorId;
    @SerializedName("Email")
    private String email;
    @SerializedName("Name")
    private String name;
    @SerializedName("ProfileImage")
    private String profileImage;

    @SerializedName("Price")
    private double price;
    @SerializedName("PriceWithFee")
    private double priceWithFee;
    @SerializedName("ContactPeriod")
    private String contactPeriod;
    @SerializedName("ContactSizeMinutes")
    private int contactSizeMinutes;
    @SerializedName("SlotPeriod")
    private String slotPeriod;
    @SerializedName("SlotSizeMinutes")
    private int slotSizeMinutes;
    @SerializedName("CurrencySymbol")
    private String currencySymbol;

    @SerializedName("CategoryName")
    private String categoryName;
    @SerializedName("UserId")
    private int userId;
    @SerializedName("Phone")
    private String phone;
    @SerializedName("Address")
    private String address;

    @SerializedName("OnlyShowInClinic")
    private boolean onlyShowInClinic;
    @SerializedName("IsVerify")
    private boolean verify;
    @SerializedName("Active")
    private boolean active;
    @SerializedName("IsFavorite")
    private boolean favorite;

    @SerializedName("IsStandByAXA")
    private boolean standByAXA;
    @SerializedName("IsStandByChiiwii")
    private boolean standByChiiwii;

    @SerializedName("CareerCertificationNumber")
    private String careerCertificationNumber;

    @SerializedName("TitleId")
    private int titleId;
    @SerializedName("StudyTitleId")
    private int studyTitleId;

    @SerializedName("PricePerMinuteFormat")
    private String pricePerMinuteFormat;
    @SerializedName("SpecialityDescription")
    private String specialityDescription;
    @SerializedName("SubCategoryName")
    private List<String> subCategoryName;

    //@SerializedName("Attachments") private String attachments;
    //@SerializedName("LanguageSkill") private String languageSkill;
    @SerializedName("DoctorSubCategories")
    private List<ItemDoctorSubCategoryDao> doctorSubCategory;
    @SerializedName("DoctorSubClinics")
    private List<ItemDoctorSubClinicsDao> doctorSubClinics;
    @SerializedName("ClosestAvailableInMinute")
    private int closestAvailableInMinute;
    @SerializedName("ClosestAvailableFormat")
    private String ClosestAvailableFormat;

    @SerializedName("LanguageSkills")
    private List<ItemDoctorLanguageSkillDao> languageSkills;
    @SerializedName("ContactTypes")
    private List<ItemDoctorContactTypeDao> contactTypes;
    @SerializedName("DocLangInformations")
    private List<ItemDoctorLangInformationDao> doctorLangInformations;

    protected ItemDoctorDao(Parcel in) {
        ClosestTime = in.readString();
        doctorId = in.readInt();
        email = in.readString();
        name = in.readString();
        profileImage = in.readString();
        price = in.readDouble();
        priceWithFee = in.readDouble();
        contactPeriod = in.readString();
        contactSizeMinutes = in.readInt();
        slotPeriod = in.readString();
        slotSizeMinutes = in.readInt();
        currencySymbol = in.readString();
        categoryName = in.readString();
        userId = in.readInt();
        phone = in.readString();
        address = in.readString();
        onlyShowInClinic = in.readByte() != 0;
        verify = in.readByte() != 0;
        active = in.readByte() != 0;
        favorite = in.readByte() != 0;
        standByAXA = in.readByte() != 0;
        standByChiiwii = in.readByte() != 0;
        careerCertificationNumber = in.readString();
        titleId = in.readInt();
        studyTitleId = in.readInt();
        pricePerMinuteFormat = in.readString();
        specialityDescription = in.readString();
        subCategoryName = in.createStringArrayList();
        closestAvailableInMinute = in.readInt();
        ClosestAvailableFormat = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ClosestTime);
        dest.writeInt(doctorId);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(profileImage);
        dest.writeDouble(price);
        dest.writeDouble(priceWithFee);
        dest.writeString(contactPeriod);
        dest.writeInt(contactSizeMinutes);
        dest.writeString(slotPeriod);
        dest.writeInt(slotSizeMinutes);
        dest.writeString(currencySymbol);
        dest.writeString(categoryName);
        dest.writeInt(userId);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeByte((byte) (onlyShowInClinic ? 1 : 0));
        dest.writeByte((byte) (verify ? 1 : 0));
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeByte((byte) (standByAXA ? 1 : 0));
        dest.writeByte((byte) (standByChiiwii ? 1 : 0));
        dest.writeString(careerCertificationNumber);
        dest.writeInt(titleId);
        dest.writeInt(studyTitleId);
        dest.writeString(pricePerMinuteFormat);
        dest.writeString(specialityDescription);
        dest.writeStringList(subCategoryName);
        dest.writeInt(closestAvailableInMinute);
        dest.writeString(ClosestAvailableFormat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemDoctorDao> CREATOR = new Creator<ItemDoctorDao>() {
        @Override
        public ItemDoctorDao createFromParcel(Parcel in) {
            return new ItemDoctorDao(in);
        }

        @Override
        public ItemDoctorDao[] newArray(int size) {
            return new ItemDoctorDao[size];
        }
    };

    public String getClosestTime() {
        return ClosestTime;
    }

    public void setClosestTime(String closestTime) {
        ClosestTime = closestTime;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceWithFee() {
        return priceWithFee;
    }

    public void setPriceWithFee(double priceWithFee) {
        this.priceWithFee = priceWithFee;
    }

    public String getContactPeriod() {
        return contactPeriod;
    }

    public void setContactPeriod(String contactPeriod) {
        this.contactPeriod = contactPeriod;
    }

    public int getContactSizeMinutes() {
        return contactSizeMinutes;
    }

    public void setContactSizeMinutes(int contactSizeMinutes) {
        this.contactSizeMinutes = contactSizeMinutes;
    }

    public String getSlotPeriod() {
        return slotPeriod;
    }

    public void setSlotPeriod(String slotPeriod) {
        this.slotPeriod = slotPeriod;
    }

    public int getSlotSizeMinutes() {
        return slotSizeMinutes;
    }

    public void setSlotSizeMinutes(int slotSizeMinutes) {
        this.slotSizeMinutes = slotSizeMinutes;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOnlyShowInClinic() {
        return onlyShowInClinic;
    }

    public void setOnlyShowInClinic(boolean onlyShowInClinic) {
        this.onlyShowInClinic = onlyShowInClinic;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isStandByAXA() {
        return standByAXA;
    }

    public void setStandByAXA(boolean standByAXA) {
        this.standByAXA = standByAXA;
    }

    public boolean isStandByChiiwii() {
        return standByChiiwii;
    }

    public void setStandByChiiwii(boolean standByChiiwii) {
        this.standByChiiwii = standByChiiwii;
    }

    public String getCareerCertificationNumber() {
        return careerCertificationNumber;
    }

    public void setCareerCertificationNumber(String careerCertificationNumber) {
        this.careerCertificationNumber = careerCertificationNumber;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getStudyTitleId() {
        return studyTitleId;
    }

    public void setStudyTitleId(int studyTitleId) {
        this.studyTitleId = studyTitleId;
    }

    public String getPricePerMinuteFormat() {
        return pricePerMinuteFormat;
    }

    public void setPricePerMinuteFormat(String pricePerMinuteFormat) {
        this.pricePerMinuteFormat = pricePerMinuteFormat;
    }

    public String getSpecialityDescription() {
        return specialityDescription;
    }

    public void setSpecialityDescription(String specialityDescription) {
        this.specialityDescription = specialityDescription;
    }

    public List<String> getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(List<String> subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public List<ItemDoctorSubClinicsDao> getDoctorSubClinics() {
        return doctorSubClinics;
    }

    public void setDoctorSubClinics(List<ItemDoctorSubClinicsDao> doctorSubClinics) {
        this.doctorSubClinics = doctorSubClinics;
    }

    public int getClosestAvailableInMinute() {
        return closestAvailableInMinute;
    }

    public void setClosestAvailableInMinute(int closestAvailableInMinute) {
        this.closestAvailableInMinute = closestAvailableInMinute;
    }

    public String getClosestAvailableFormat() {
        return ClosestAvailableFormat;
    }

    public void setClosestAvailableFormat(String closestAvailableFormat) {
        ClosestAvailableFormat = closestAvailableFormat;
    }

    public List<ItemDoctorLanguageSkillDao> getLanguageSkills() {
        return languageSkills;
    }

    public void setLanguageSkills(List<ItemDoctorLanguageSkillDao> languageSkills) {
        this.languageSkills = languageSkills;
    }

    public List<ItemDoctorContactTypeDao> getContactTypes() {
        return contactTypes;
    }

    public void setContactTypes(List<ItemDoctorContactTypeDao> contactTypes) {
        this.contactTypes = contactTypes;
    }

    public List<ItemDoctorLangInformationDao> getDoctorLangInformations() {
        return doctorLangInformations;
    }

    public void setDoctorLangInformations(List<ItemDoctorLangInformationDao> doctorLangInformations) {
        this.doctorLangInformations = doctorLangInformations;
    }

    public List<ItemDoctorSubCategoryDao> getDoctorSubCategory() {
        return doctorSubCategory;
    }

    public void setDoctorSubCategory(List<ItemDoctorSubCategoryDao> doctorSubCategory) {
        this.doctorSubCategory = doctorSubCategory;
    }
}
