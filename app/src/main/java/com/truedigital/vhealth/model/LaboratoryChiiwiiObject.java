package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LaboratoryChiiwiiObject extends GridMatrixDataObject {

    @SerializedName("FieldId")
    private int fieldId;
    @SerializedName("LabDate")
    private String labDateString;
    private Date labDate;
    @SerializedName("PatientChiiwiiLabId")
    private int patientChiiwiiLabId;
    @SerializedName("Value")
    private String value;
    @SerializedName("PatientMenuId")
    private int patientMenuId;


    public int getFieldId() { return fieldId;  }

    public void setFieldId(int vaccineId) {  this.fieldId = fieldId; }

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
        if(labDate != null)
        {
            this.labDateString = ConvertDate.convertDateToServiceFormat(labDate);
        }
        else
        {
            this.labDateString = null;
        }
    }

    public int getPatientChiiwiiLabId() { return patientChiiwiiLabId;  }

    public void setPatientChiiwiiLabId(int patientChiiwiiLabId) {  this.patientChiiwiiLabId = patientChiiwiiLabId; }

    public String getValue() { return value;  }

    public void setValue(String value) {  this.value = value; }

    public int getPatientMenuId() { return patientMenuId;  }

    public void setPatientMenuId(int patientMenuId) {  this.patientMenuId = patientMenuId; }


}
