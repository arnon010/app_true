package com.truedigital.vhealth.ui.appointment;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class AppointmentFragmentPresenter extends BaseMvpPresenter<AppointmentFragmentInterface.View>
        implements AppointmentFragmentInterface.Presenter {

    public static AppointmentFragmentInterface.Presenter create() {
        return new AppointmentFragmentPresenter();
    }
}
