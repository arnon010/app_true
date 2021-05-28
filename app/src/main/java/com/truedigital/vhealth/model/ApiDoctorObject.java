package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nilecon on 2/6/2017 AD.
 */

public class ApiDoctorObject {

    @SerializedName("Account")
    private AccountObject accountObject;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public AccountObject getAccountObject() {
        return accountObject;
    }

    public void setAccountObject(AccountObject accountObject) {
        this.accountObject = accountObject;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public class AccountObject {
        @SerializedName("LastName")
        private String lastname;
        @SerializedName("Type")
        private int type;
        @SerializedName("Specialty")
        private ArrayList<Skill> specialtyList;
        @SerializedName("SubSpecialty")
        private ArrayList<Skill> subSpecialtyList;
        @SerializedName("Id")
        private int id;
        @SerializedName("UserId")
        private int userId;
        @SerializedName("Name")
        private String name;
        @SerializedName("TitleName")
        private String titleName;
        @SerializedName("TitleStudy")
        private String titleStudy;
        @SerializedName("LanguageSkill")
        private String languageSkill;
        @SerializedName("LanguageId")
        private int languageId;
        @SerializedName("BornName")
        private String bornName;
        @SerializedName("MDCard")
        private String mDCard;
        @SerializedName("Address")
        private String address;
        @SerializedName("Phone")
        private String phone;
        @SerializedName("Email")
        private String email;
        @SerializedName("BackupEmail")
        private String backupEmail;
        @SerializedName("SpecialtyId")
        private String specialtyId;
        @SerializedName("SpecialtyText")
        private String specialtyText;
        @SerializedName("SubSpecialtyId")
        private String subSpecialtyId;
        @SerializedName("SubSpecialtyText")
        private String subSpecialtyText;
        @SerializedName("SymptomId")
        private String symptomId;
        @SerializedName("SymptomText")
        private String symptomText;
        @SerializedName("ProfessionalCertificate")
        private String professionalCertificate;
        @SerializedName("TypeProCer")
        private String typeProCer;
        @SerializedName("IDCard")
        private String idCard;
        @SerializedName("TypeIDCard")
        private String typeIDCard;
        @SerializedName("ProfileImage")
        private String profileImage;
        @SerializedName("IDCardImage")
        private String idCardImage;
        @SerializedName("ProfessionalCertificateImage")
        private String professionalCertificateImage;
        @SerializedName("ContactPrice")
        private int contactPrice;
        @SerializedName("Viewer")
        private int viewer;
        @SerializedName("AmountRate")
        private float amountRate;
        @SerializedName("IsChat")
        private boolean isChat;
        @SerializedName("IsVoice")
        private boolean isVoice;
        @SerializedName("IsVideo")
        private boolean isVideo;
        @SerializedName("Diploma")
        private String diploma;
        @SerializedName("CareerHistory")
        private String careerHistory;

        @SerializedName("IsFavorited") private boolean favorite;

        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
        }

        public String getDiploma() {
            return diploma;
        }

        public void setDiploma(String diploma) {
            this.diploma = diploma;
        }

        public String getCareerHistory() {
            return careerHistory;
        }

        public void setCareerHistory(String careerHistory) {
            this.careerHistory = careerHistory;
        }

        public boolean isChat() {
            return isChat;
        }

        public void setChat(boolean chat) {
            isChat = chat;
        }

        public boolean isVoice() {
            return isVoice;
        }

        public void setVoice(boolean voice) {
            isVoice = voice;
        }

        public boolean isVideo() {
            return isVideo;
        }

        public void setVideo(boolean video) {
            isVideo = video;
        }

        public int getContactPrice() {
            return contactPrice;
        }

        public void setContactPrice(int contactPrice) {
            this.contactPrice = contactPrice;
        }

        public int getLanguageId() {
            return languageId;
        }

        public void setLanguageId(int languageId) {
            this.languageId = languageId;
        }

        public ArrayList<Skill> getSpecialtyList() {
            return specialtyList;
        }

        public void setSpecialtyList(ArrayList<Skill> specialtyList) {
            this.specialtyList = specialtyList;
        }

        public ArrayList<Skill> getSubSpecialtyList() {
            return subSpecialtyList;
        }

        public void setSubSpecialtyList(ArrayList<Skill> subSpecialtyList) {
            this.subSpecialtyList = subSpecialtyList;
        }

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        public String getTitleStudy() {
            return titleStudy;
        }

        public void setTitleStudy(String titleStudy) {
            this.titleStudy = titleStudy;
        }

        public String getLanguageSkill() {
            return languageSkill;
        }

        public void setLanguageSkill(String languageSkill) {
            this.languageSkill = languageSkill;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBornName() {
            return bornName;
        }

        public void setBornName(String bornName) {
            this.bornName = bornName;
        }

        public String getmDCard() {
            return mDCard;
        }

        public void setmDCard(String mDCard) {
            this.mDCard = mDCard;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBackupEmail() {
            return backupEmail;
        }

        public void setBackupEmail(String backupEmail) {
            this.backupEmail = backupEmail;
        }

        public String getSpecialtyId() {
            return specialtyId;
        }

        public void setSpecialtyId(String specialtyId) {
            this.specialtyId = specialtyId;
        }

        public String getSpecialtyText() {
            return specialtyText;
        }

        public void setSpecialtyText(String specialtyText) {
            this.specialtyText = specialtyText;
        }

        public String getSubSpecialtyId() {
            return subSpecialtyId;
        }

        public void setSubSpecialtyId(String subSpecialtyId) {
            this.subSpecialtyId = subSpecialtyId;
        }

        public String getSubSpecialtyText() {
            return subSpecialtyText;
        }

        public void setSubSpecialtyText(String subSpecialtyText) {
            this.subSpecialtyText = subSpecialtyText;
        }

        public String getSymptomId() {
            return symptomId;
        }

        public void setSymptomId(String symptomId) {
            this.symptomId = symptomId;
        }

        public String getSymptomText() {
            return symptomText;
        }

        public void setSymptomText(String symptomText) {
            this.symptomText = symptomText;
        }

        public String getProfessionalCertificate() {
            return professionalCertificate;
        }

        public void setProfessionalCertificate(String professionalCertificate) {
            this.professionalCertificate = professionalCertificate;
        }

        public String getTypeProCer() {
            return typeProCer;
        }

        public void setTypeProCer(String typeProCer) {
            this.typeProCer = typeProCer;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getTypeIDCard() {
            return typeIDCard;
        }

        public void setTypeIDCard(String typeIDCard) {
            this.typeIDCard = typeIDCard;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getIdCardImage() {
            return idCardImage;
        }

        public void setIdCardImage(String idCardImage) {
            this.idCardImage = idCardImage;
        }

        public String getProfessionalCertificateImage() {
            return professionalCertificateImage;
        }

        public void setProfessionalCertificateImage(String professionalCertificateImage) {
            this.professionalCertificateImage = professionalCertificateImage;
        }

        public int getViewer() {
            return viewer;
        }

        public void setViewer(int viewer) {
            this.viewer = viewer;
        }

        public float getAmountRate() {
            return amountRate;
        }

        public void setAmountRate(float amountRate) {
            this.amountRate = amountRate;
        }

        public class Skill{
            @SerializedName("Id")
            private int id;
            @SerializedName("Detail")
            private String detail;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }
    }
}
