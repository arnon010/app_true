package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorScheduleTimeDao {

    @SerializedName("ScheduleTime") private String ScheduleTime;
    @SerializedName("Selected") private boolean selected;

    public ItemDoctorScheduleTimeDao() {
    }

    public String getScheduleTime() {
        return ScheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        ScheduleTime = scheduleTime;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
