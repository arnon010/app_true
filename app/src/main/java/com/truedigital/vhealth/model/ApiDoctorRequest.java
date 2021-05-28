package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ApiDoctorRequest extends NormalResponseObject {

    /*
    Doctor:{
        "Doctor":{"Email": "par1112@gmail.com","Password" : "123456","ConfirmPassword" : "123456","Phone": "0981188188", "OnlyShowInClinic" : false, "Active" : true, "IsVerify" : true, "CurrencyCode":"BTH","Price":500},
        "DoctorLangs": [{"LanguageCode": "en-US", "Title": "ผศ", "StudyTitle": "ดร", "FirstName" : "จรูญ", "LastName" : "ปรีชา", "SpecialityDescription": "ไม่มี", "CertificateDetail" : "ไม่มี", "CareerHistory" : "ไม่มี"}, {"LanguageCode": "th-TH", "Title": "ผศ", "StudyTitle": "ดร", "FirstName" : "จรูญ", "LastName" : "ปรีช", "SpecialityDescription" : "ไม่มี", "CertificateDetail" : "ไม่มี", "CareerHistory" : "ไม่มี"}],
        "DoctorSubCategories":[{"SubCategoryId": 7},{"SubCategoryId":7518}],
        "DoctorClinicSubCategories":[{"ClinicSubCategoryId":1683},{"ClinicSubCategoryId":1686}],
        "DoctorSkillLangs":[{"LanguageSkillCode":"th-TH"},{"LanguageSkillCode":"en-US"}],
        "DoctorAttachments":[{"AttachmentTypeCode":"CC","IsDelete":false,"ParameterName":"CC_1"},{"AttachmentTypeCode":"CC","IsDelete":false,"ParameterName":"CC_2"}]}
    */

    @SerializedName("Doctor") private ItemDoctorRequestDao Doctor;
    @SerializedName("DoctorLangs") private List<ItemDoctorLangsDao> DoctorLangs;
    @SerializedName("DoctorSubCategories") private List<ItemDoctorSubCategoriesDao> DoctorSubCategories;
    @SerializedName("DoctorClinicSubCategories") private List<ItemDoctorClinicSubCategoriesDao> DoctorClinicSubCategories;
    @SerializedName("DoctorSkillLangs") private List<ItemDoctorSkillLangsDao> DoctorSkillLangs;
    @SerializedName("DoctorAttachments") private List<ItemDoctorAttachmentsDao> DoctorAttachments;
    public ItemDoctorRequestDao getDoctor() {
        return Doctor;
    }

    public void setDoctor(ItemDoctorRequestDao doctor) {
        Doctor = doctor;
    }

    public List<ItemDoctorLangsDao> getDoctorLangs() {
        return DoctorLangs;
    }

    public void setDoctorLangs(List<ItemDoctorLangsDao> doctorLangs) {
        DoctorLangs = doctorLangs;
    }

    public List<ItemDoctorSubCategoriesDao> getDoctorSubCategories() {
        return DoctorSubCategories;
    }

    public void setDoctorSubCategories(List<ItemDoctorSubCategoriesDao> doctorSubCategories) {
        DoctorSubCategories = doctorSubCategories;
    }

    public List<ItemDoctorClinicSubCategoriesDao> getDoctorClinicSubCategories() {
        return DoctorClinicSubCategories;
    }

    public void setDoctorClinicSubCategories(List<ItemDoctorClinicSubCategoriesDao> doctorClinicSubCategories) {
        DoctorClinicSubCategories = doctorClinicSubCategories;
    }

    public List<ItemDoctorSkillLangsDao> getDoctorSkillLangs() {
        return DoctorSkillLangs;
    }

    public void setDoctorSkillLangs(List<ItemDoctorSkillLangsDao> doctorSkillLangs) {
        DoctorSkillLangs = doctorSkillLangs;
    }

    public List<ItemDoctorAttachmentsDao> getDoctorAttachments() {
        return DoctorAttachments;
    }

    public void setDoctorAttachments(List<ItemDoctorAttachmentsDao> doctorAttachments) {
        DoctorAttachments = doctorAttachments;
    }
}
