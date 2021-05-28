package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemAppointmentShortnoteDao extends NormalResponseObject {

    @SerializedName("AppointmentNumber") private String appointmentNumber;
    @SerializedName("ShortNote") private String shortNote;
    @SerializedName("DeleteAttachments") private List<Integer> deleteAttachments;
    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getShortNote() {
        return shortNote;
    }

    public void setShortNote(String shortNote) {
        this.shortNote = shortNote;
    }

    public List<Integer> getDeleteAttachments() {
        return deleteAttachments;
    }

    public void setDeleteAttachments(List<Integer> deleteAttachments) {
        this.deleteAttachments = deleteAttachments;
    }
}