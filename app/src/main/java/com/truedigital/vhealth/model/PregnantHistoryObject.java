package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class PregnantHistoryObject implements Cloneable {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("PregnancyId")
    private int pregnancyId;
    @SerializedName("PatientMenuId")
    private int patientMenuId;
    @SerializedName("Title")
    private String title;
    @SerializedName("Place")
    private String place;
    @SerializedName("Detail")
    private String detail;
    @SerializedName("RecordDate")
    private String recordDateString;
    private Date recordDate;
    @SerializedName("Attachments")
    private ArrayList<PregnantHistoryImageObject> attachmentList;

    private String menuCode;
    private String patientMenuName;


    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPregnancyId() {
        return pregnancyId;
    }

    public void setPregnancyId(int pregnancyId) {
        this.pregnancyId = pregnancyId;
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

    public ArrayList<PregnantHistoryImageObject> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(ArrayList<PregnantHistoryImageObject> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getImageUrl() {
        if (attachmentList != null && attachmentList.size() > 0) {
            for (PregnantHistoryImageObject attac : attachmentList) {
                if (!attac.getIsVideo()) {
                    return attac.getImageUrl();
                }
            }
        }
        return "";
    }

    public PregnantHistoryObject clone() {
        try {
            return (PregnantHistoryObject) super.clone();
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

    public String getPatientMenuName() {
        return patientMenuName;
    }

    public void setPatientMenuName(String patientMenuName) {
        this.patientMenuName = patientMenuName;
    }

}
