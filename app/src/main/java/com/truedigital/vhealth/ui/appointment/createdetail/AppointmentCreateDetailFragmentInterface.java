package com.truedigital.vhealth.ui.appointment.createdetail;

import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class AppointmentCreateDetailFragmentInterface {

    public interface View extends BaseMvpInterface.View {

        void openAppointmentConfirm(ItemDoctorDao data);

        void loadDataAppointment();

        void setDataAppointment();

        String getSymptom();

        String getEmail();

        String getTelephone();

        void onErrorEmail();

        void onErrorTelephone();

        boolean isValid();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<AppointmentCreateDetailFragmentInterface.View> {
        void loadData();
    }
}
