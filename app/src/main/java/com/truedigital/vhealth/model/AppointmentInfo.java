package com.truedigital.vhealth.model;

/**
 * Created by songkrit on 12/10/2017 AD.
 */

public class AppointmentInfo {
    private String appointmentNumber;
    private ApiUserObject.AccountObject patientInfo;
    private ApiDoctorObject.AccountObject doctorInfo;

    public AppointmentInfo() {

    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public ApiUserObject.AccountObject getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(ApiUserObject.AccountObject patientInfo) {
        this.patientInfo = patientInfo;
    }

    public ApiDoctorObject.AccountObject getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(ApiDoctorObject.AccountObject doctorInfo) {
        this.doctorInfo = doctorInfo;
    }
}
