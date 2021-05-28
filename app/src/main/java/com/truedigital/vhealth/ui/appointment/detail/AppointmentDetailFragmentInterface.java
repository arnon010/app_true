package com.truedigital.vhealth.ui.appointment.detail;

import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class AppointmentDetailFragmentInterface {


    public interface View extends BaseMvpInterface.View {

        String getAppointmentNumber();

        void setData(ItemAppointmentDao data);

        void updateView();

        void openAppointmentCancel(ItemAppointmentDao data);

        void openRoom();

        void openDoctorNoteConfirm();

        int getAppointmentType();

        String getAppointmentContactType();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<AppointmentDetailFragmentInterface.View> {
        void getAppointmentInfo(final String appointmentNumber);
    }
}
