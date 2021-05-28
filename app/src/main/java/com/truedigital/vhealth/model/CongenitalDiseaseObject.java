package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CongenitalDiseaseObject implements Cloneable {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("CongenitalDiseaseId")
    private int congenitalDiseaseId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("Title")
    private String title;
    @SerializedName("Detail")
    private String detail;
    @SerializedName("Attachments")
    private ArrayList<CongenitalDiseaseImageObject> attachmentList;

    private String menuCode;


    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getCongenitalDiseaseId() {
        return congenitalDiseaseId;
    }

    public void setCongenitalDiseaseId(int congenitalDiseaseId) {
        this.congenitalDiseaseId = congenitalDiseaseId;
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

    public ArrayList<CongenitalDiseaseImageObject> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(ArrayList<CongenitalDiseaseImageObject> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getImageUrl() {
        if (attachmentList != null && attachmentList.size() > 0) {
            for (CongenitalDiseaseImageObject attac : attachmentList) {
                if (!attac.getIsVideo()) {
                    return attac.getImageUrl();
                }
            }
        }
        return "";
    }

    public CongenitalDiseaseObject clone() {
        try {
            return (CongenitalDiseaseObject) super.clone();
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
