package com.truedigital.vhealth.ui.appointment;


import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class AppointmentFragmentInterface {


    public interface View extends BaseMvpInterface.View {
    }

    public interface Presenter extends BaseMvpInterface.Presenter<AppointmentFragmentInterface.View> {
    }
}
