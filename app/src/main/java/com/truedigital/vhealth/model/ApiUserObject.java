package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nilecon on 2/6/2017 AD.
 */

public class ApiUserObject {

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

            @SerializedName("UserId")
            private int userId;
            @SerializedName("Email")
            private String email;
            @SerializedName("JobName")
            private String jobName;
            @SerializedName("JobId")
            private int jobId;
            @SerializedName("FacebookId")
            private String facebookId;
            @SerializedName("Type")
            private int type;
            @SerializedName("UserName")
            private String userName;
            @SerializedName("Name")
            private String name;
            @SerializedName("Lastname")
            private String lastname;
            @SerializedName("BirthDay")
            private String birthday;
            @SerializedName("CongenitalDisease") //โรค
            private String congenitalDisease;
            @SerializedName("BeAllergic") //แพ้ยา
            private String beAllergic;
            @SerializedName("FoodAllergy") //แพ้อาหาร
            private String foodAllergy;
            @SerializedName("ImageProfile")
            private String imageProfile;
            @SerializedName("CreateDate")
            private String createDate;
            @SerializedName("ModifyDate")
            private String modifyDate;

            @SerializedName("ContactTelephone") private String contactTelephone;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getJobName() {
                return jobName;
            }

            public void setJobName(String jobName) {
                this.jobName = jobName;
            }

            public int getJobId() {
                return jobId;
            }

            public void setJobId(int jobId) {
                this.jobId = jobId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getFacebookId() {
                return facebookId;
            }

            public void setFacebookId(String facebookId) {
                this.facebookId = facebookId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLastname() {
                return lastname;
            }

            public void setLastname(String lastname) {
                this.lastname = lastname;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCongenitalDisease() {
                return congenitalDisease;
            }

            public void setCongenitalDisease(String congenitalDisease) {
                this.congenitalDisease = congenitalDisease;
            }

            public String getBeAllergic() {
                return beAllergic;
            }

            public void setBeAllergic(String beAllergic) {
                this.beAllergic = beAllergic;
            }

            public String getFoodAllergy() {
                return foodAllergy;
            }

            public void setFoodAllergy(String foodAllergy) {
                this.foodAllergy = foodAllergy;
            }

            public String getImageProfile() {
                return imageProfile;
            }

            public void setImageProfile(String imageProfile) {
                this.imageProfile = imageProfile;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getModifyDate() {
                return modifyDate;
            }

            public void setModifyDate(String modifyDate) {
                this.modifyDate = modifyDate;
            }

            public String getContactTelephone() {
                return contactTelephone;
            }

            public void setContactTelephone(String contactTelephone) {
                this.contactTelephone = contactTelephone;
            }
        }
}
