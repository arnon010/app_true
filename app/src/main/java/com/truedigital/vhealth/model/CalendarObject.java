package com.truedigital.vhealth.model;

import java.util.Date;

/**
 * Created by nilecon on 1/20/2017 AD.
 */

public class CalendarObject {

    private Date date;

    private boolean isFree;

    private boolean isActive;

    private int day;
    private boolean HasOpenSlot;
    private boolean HasAvailableSlot;
    private boolean isSelected;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isHasOpenSlot() {
        return HasOpenSlot;
    }

    public void setHasOpenSlot(boolean hasOpenSlot) {
        HasOpenSlot = hasOpenSlot;
    }

    public boolean isHasAvailableSlot() {
        return HasAvailableSlot;
    }

    public void setHasAvailableSlot(boolean hasAvailableSlot) {
        HasAvailableSlot = hasAvailableSlot;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
