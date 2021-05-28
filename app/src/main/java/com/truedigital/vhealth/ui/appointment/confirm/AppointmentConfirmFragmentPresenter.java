package com.truedigital.vhealth.ui.appointment.confirm;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class AppointmentConfirmFragmentPresenter extends BaseMvpPresenter<AppointmentConfirmFragmentInterface.View>
        implements AppointmentConfirmFragmentInterface.Presenter {

    public static AppointmentConfirmFragmentInterface.Presenter create() {
        return new AppointmentConfirmFragmentPresenter();
    }
}
