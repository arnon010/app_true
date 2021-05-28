package com.truedigital.vhealth.ui.appointment.cancel;

import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class AppointmentCancelFragmentInterface {

    public interface View extends BaseMvpInterface.View {
        String getAppointmentNumber();

        String getReason();

        void openCancelConfirm();

        void showSuccess();

        void onSuccess();

        void setData(ItemAppointmentDao data);

        void updateView();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<AppointmentCancelFragmentInterface.View> {
        void getAppointmentInfo(final String appointmentNumber);

        void callCancelAppointment(final String appointmentNumber, String reason);
    }
}
