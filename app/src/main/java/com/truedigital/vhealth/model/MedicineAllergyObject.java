package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MedicineAllergyObject implements Cloneable {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("MedicineAllergyId")
    private int medicineAllergyId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("Title")
    private String title;
    @SerializedName("Detail")
    private String detail;
    @SerializedName("Attachments")
    private ArrayList<MedicineAllergyImageObject> attachmentList;

    private String menuCode;


    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getMedicineAllergyId() {
        return medicineAllergyId;
    }

    public void setMedicineAllergyId(int medicineAllergyId) {
        this.medicineAllergyId = medicineAllergyId;
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

    public ArrayList<MedicineAllergyImageObject> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(ArrayList<MedicineAllergyImageObject> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getImageUrl() {
        if (attachmentList != null && attachmentList.size() > 0) {
            for (MedicineAllergyImageObject attac : attachmentList) {
                if (!attac.getIsVideo()) {
                    return attac.getImageUrl();
                }
            }
        }
        return "";
    }

    public MedicineAllergyObject clone() {
        try {
            return (MedicineAllergyObject) super.clone();
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
