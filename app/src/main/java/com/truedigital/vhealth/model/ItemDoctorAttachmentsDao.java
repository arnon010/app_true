package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorAttachmentsDao extends NormalResponseObject {

    @SerializedName("AttachmentTypeCode") private String AttachmentTypeCode;
    @SerializedName("IsDelete") private boolean IsDelete;
    @SerializedName("ParameterName") private String ParameterName;

    public String getAttachmentTypeCode() {
        return AttachmentTypeCode;
    }

    public void setAttachmentTypeCode(String attachmentTypeCode) {
        AttachmentTypeCode = attachmentTypeCode;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public String getParameterName() {
        return ParameterName;
    }

    public void setParameterName(String parameterName) {
        ParameterName = parameterName;
    }
}
