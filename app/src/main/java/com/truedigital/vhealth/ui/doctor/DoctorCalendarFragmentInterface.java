package com.truedigital.vhealth.ui.doctor;


import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class DoctorCalendarFragmentInterface {


    public interface View extends BaseMvpInterface.View{

        int getDoctorId();
        void setDoctorCalendar();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<DoctorCalendarFragmentInterface.View>{
        void loadData();
        void getDoctorTimeSchedules(int doctorId);
    }
}
