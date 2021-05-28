package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songkrit on 12/7/2017 AD.
 */

public class ApiAppointmentStatus extends NormalResponseObject {

    @SerializedName("CanCancel") private boolean CanCancel;
    @SerializedName("CanStart") private boolean CanStart;
    @SerializedName("CanEnd") private boolean CanEnd;

    public ApiAppointmentStatus() {
    }

    public boolean isCanCancel() {
        return CanCancel;
    }

    public void setCanCancel(boolean canCancel) {
        CanCancel = canCancel;
    }

    public boolean isCanStart() {
        return CanStart;
    }

    public void setCanStart(boolean canStart) {
        CanStart = canStart;
    }

    public boolean isCanEnd() {
        return CanEnd;
    }

    public void setCanEnd(boolean canEnd) {
        CanEnd = canEnd;
    }
}
