package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class MedicationHistoryObject implements Cloneable {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("MedicinationId")
    private int medicinationId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("Title")
    private String title;
    @SerializedName("Place")
    private String place;
    @SerializedName("Detail")
    private String detail;
    @SerializedName("IsChiiwiiProduct")
    private boolean isChiiwiiProduct;
    @SerializedName("RecommendItemId")
    private int recommendItemId;
    @SerializedName("RecordDate")
    private String recordDateString;
    private Date recordDate;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("Unit")
    private String unit;
    @SerializedName("HowToUse")
    private String howToUse;
    @SerializedName("Attachments")
    private ArrayList<MedicationHistoryImageObject> attachmentList;
    private String menuCode;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getMedicinationId() {
        return medicinationId;
    }

    public void setMedicinationId(int medicinationId) {
        this.medicinationId = medicinationId;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean getIsChiiwiiProduct() {
        return isChiiwiiProduct;
    }

    public void setIsChiiwiiProduct(boolean isChiiwiiProduct) {
        this.isChiiwiiProduct = isChiiwiiProduct;
    }

    public int getRecommendItemId() {
        return recommendItemId;
    }

    public void setRecommendItemId(int recommendItemId) {
        this.recommendItemId = recommendItemId;
    }

    public String getRecordDateString() {
        return recordDateString;
    }

    public void setRecordDateString(String recordDateString) {
        this.recordDateString = recordDateString;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
        if (recordDate != null) {
            this.recordDateString = ConvertDate.convertDateServer(recordDate);
        } else {
            this.recordDateString = null;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public ArrayList<MedicationHistoryImageObject> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(ArrayList<MedicationHistoryImageObject> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getImageUrl() {
        if (attachmentList != null && attachmentList.size() > 0) {
            for (MedicationHistoryImageObject attac : attachmentList) {
                if (!attac.getIsVideo()) {
                    return attac.getImageUrl();
                }
            }
        }
        return "";
    }

    public MedicationHistoryObject clone() {
        try {
            return (MedicationHistoryObject) super.clone();
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
