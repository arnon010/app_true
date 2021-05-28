package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemTimeSlotDao {

    @SerializedName("Times") private String Times;
    @SerializedName("IsOpen") private boolean open;
    @SerializedName("IsAvailable") private boolean available;
    @SerializedName("IsRepeatDay") private boolean IsRepeatDay;

    public String getTimes() {
        return Times;
    }

    public void setTimes(String times) {
        Times = times;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isRepeatDay() {
        return IsRepeatDay;
    }

    public void setRepeatDay(boolean repeatDay) {
        IsRepeatDay = repeatDay;
    }
}
