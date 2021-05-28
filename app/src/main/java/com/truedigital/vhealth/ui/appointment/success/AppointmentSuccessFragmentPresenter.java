package com.truedigital.vhealth.ui.appointment.success;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class AppointmentSuccessFragmentPresenter extends BaseMvpPresenter<AppointmentSuccessFragmentInterface.View>
        implements AppointmentSuccessFragmentInterface.Presenter {

    public static AppointmentSuccessFragmentInterface.Presenter create() {
        return new AppointmentSuccessFragmentPresenter();
    }
}
