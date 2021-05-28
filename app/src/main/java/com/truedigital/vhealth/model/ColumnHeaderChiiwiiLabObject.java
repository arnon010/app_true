package com.truedigital.vhealth.model;

import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ColumnHeaderChiiwiiLabObject {

    @SerializedName("LabDate")
    private String labDateString;
    private Date labDate;

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

}
