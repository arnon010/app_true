package com.truedigital.vhealth.utils;

import com.truedigital.vhealth.R;

/**
 * Created by songkrit on 8/23/2018 AD.
 */

public class AppointmentUtil {

    public final static int APPOINTMENT_INCOMING = 0;
    public final static int APPOINTMENT_HISTORY = 1;
    public final static int APPOINTMENT_CANCEL = 2;

    private int appointmentType;

    public int getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(int appointmentType) {
        this.appointmentType = appointmentType;
    }

    public static int getTagTitle(int position) {
        int[] resId = {R.string.appointment_tag_new, R.string.appointment_tag_past, R.string.appointment_tag_cancel};
        return resId[position];
    }

    public static int getButtonTitle(boolean isDoctor, int position, boolean isSold) {
        if (isDoctor) {
            return getButtonDoctorTitle(position);
        }
        else {
            return getButtonUserTitle(position, isSold);
        }
    }

    private static int getButtonDoctorTitle(int position) {
        int[] resId = {R.string.appointment_button_start, R.string.appointment_button_send_note, R.string.appointment_button_view_cancel};
        return resId[position];
    }

    /*
    private static int getButtonUserTitle(int position) {
        int[] resId = {R.string.appointment_button_start, R.string.appointment_button_view_note, R.string.appointment_button_view_cancel};
        return resId[position];
    }
    */

    private static int getButtonUserTitle(int position, boolean isReceiveMedicine) {

        int[] resId = {R.string.appointment_button_start, R.string.doctor_note_confirm_title, R.string.appointment_button_view_cancel};
        if (isReceiveMedicine) {
            //resId[1] = R.string.appointment_button_view_note_with_medicine;
        }

        return resId[position];
    }

}
