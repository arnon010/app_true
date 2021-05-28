package com.truedigital.vhealth.ui.appointment.createdetail;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class AppointmentCreateDetailFragmentPresenter extends BaseMvpPresenter<AppointmentCreateDetailFragmentInterface.View>
        implements AppointmentCreateDetailFragmentInterface.Presenter {

    public static AppointmentCreateDetailFragmentInterface.Presenter create() {
        return new AppointmentCreateDetailFragmentPresenter();
    }

    @Override
    public void loadData() {
    }
}
