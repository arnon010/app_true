package com.truedigital.vhealth.ui.appointment.confirm;

import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class AppointmentConfirmFragmentInterface {

    public interface View extends BaseMvpInterface.View {

        void openAppointmentSuccess(ItemDoctorDao data, ApiAppointmentRequest appointmentData, double discountPrice);

        void showSuccess(String appointmentNumber);

        void showSuccess(String appointmentNumber, double discountPrice);

        int getDoctorId();

        String getOmiseToken();

        void loadDataAppointment();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<AppointmentConfirmFragmentInterface.View> {
    }
}
