package com.truedigital.vhealth.ui.doctor;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class DoctorCalendarFragmentPresenter extends BaseMvpPresenter<DoctorCalendarFragmentInterface.View>
        implements DoctorCalendarFragmentInterface.Presenter{

    public static DoctorCalendarFragmentInterface.Presenter create(){
        return new DoctorCalendarFragmentPresenter();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void getDoctorTimeSchedules(int doctorId) {

    }
}
