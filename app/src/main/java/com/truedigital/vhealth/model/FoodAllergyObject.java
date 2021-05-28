package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodAllergyObject implements Cloneable {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("FoodAllergyId")
    private int foodAllergyId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("Title")
    private String title;
    @SerializedName("Detail")
    private String detail;
    @SerializedName("Attachments")
    private ArrayList<FoodAllergyImageObject> attachmentList;

    private String menuCode;


    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getFoodAllergyId() {
        return foodAllergyId;
    }

    public void setFoodAllergyId(int foodAllergyId) {
        this.foodAllergyId = foodAllergyId;
    }

    public int getPatientMenuId() {
        return patientMenuId;
    }

    public void setPatientMenuId(int patientMenuId) {
        this.patientMenuId = patientMenuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ArrayList<FoodAllergyImageObject> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(ArrayList<FoodAllergyImageObject> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getImageUrl() {
        if (attachmentList != null && attachmentList.size() > 0) {
            for (FoodAllergyImageObject attac : attachmentList) {
                if (!attac.getIsVideo()) {
                    return attac.getImageUrl();
                }
            }
        }
        return "";
    }

    public FoodAllergyObject clone() {
        try {
            return (FoodAllergyObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }


}