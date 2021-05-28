package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class LaboratoryOtherObject implements Cloneable{

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("LabId")
    private int labId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("Title")
    private String title;
    @SerializedName("Place")
    private String place;
    @SerializedName("Detail")
    private String detail;
    @SerializedName("LabDate")
    private String labDateString;
    private Date labDate;
    @SerializedName("Attachments")
    private ArrayList<LaboratoryOtherImageObject> attachmentList;
    private String menuCode;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
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

    public String getLabDateString() {
        return labDateString;
    }

    public void setLabDateString(String labDateString) {
        this.labDateString = labDateString;
    }

    public Date getLabDate() {
        return labDate;
    }

    public void setLabDate(Date labDate) {
        this.labDate = labDate;
        if (labDate != null) {
            this.labDateString = ConvertDate.convertDateServer(labDate);
        } else {
            this.labDateString = null;
        }
    }

    public ArrayList<LaboratoryOtherImageObject> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(ArrayList<LaboratoryOtherImageObject> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getImageUrl() {
        if (attachmentList != null && attachmentList.size() > 0) {
            for (LaboratoryOtherImageObject attac : attachmentList) {
                if (!attac.getIsVideo()) {
                    return attac.getImageUrl();
                }
            }
        }
        return "";
    }

    public LaboratoryOtherObject clone() {
        try {
            return (LaboratoryOtherObject) super.clone();
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
