package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ApiDoctorSchedules extends NormalResponseObject {

    @SerializedName("Data") private List<Schedules> Schedules;

    public List<ApiDoctorSchedules.Schedules> getSchedules() {
        return Schedules;
    }

    public void setSchedules(List<ApiDoctorSchedules.Schedules> schedules) {
        Schedules = schedules;
    }

    public class Schedules {
        @SerializedName("Day") private int Day;
        @SerializedName("HasOpenSlot") private boolean HasOpenSlot;
        @SerializedName("HasAppointment") private boolean HasAppointment;
        @SerializedName("HasAvailableSlot") private boolean HasAvailableSlot;

        public int getDay() {
            return Day;
        }

        public void setDay(int day) {
            Day = day;
        }

        public boolean isHasOpenSlot() {
            return HasOpenSlot;
        }

        public void setHasOpenSlot(boolean hasOpenSlot) {
            HasOpenSlot = hasOpenSlot;
        }

        public boolean isHasAppointment() {
            return HasAppointment;
        }

        public void setHasAppointment(boolean hasAppointment) {
            HasAppointment = hasAppointment;
        }

        public boolean isHasAvailableSlot() {
            return HasAvailableSlot;
        }

        public void setHasAvailableSlot(boolean hasAvailableSlot) {
            HasAvailableSlot = hasAvailableSlot;
        }
    }
}
