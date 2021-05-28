package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.model.ImageObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemAppointmentOPDCardDao extends NormalResponseObject {

    @SerializedName("AppointmentNumber") private String appointmentNumber;
    @SerializedName("OPDCard") private String opdCard;
    @SerializedName("Attachments") private List<ImageObject> attachments;

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getOpdCard() {
        return opdCard;
    }

    public void setOpdCard(String opdCard) {
        this.opdCard = opdCard;
    }

    public List<ImageObject> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ImageObject> attachments) {
        this.attachments = attachments;
    }
}