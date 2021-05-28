package com.truedigital.vhealth.ui.appointment.success;


import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class AppointmentSuccessFragmentInterface {


    public interface View extends BaseMvpInterface.View {

        void showPriceDetail();

        void hidePriceDetail();

        void openListAppointment();

        void loadDataAppointment();

        double getDiscountPrice();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<AppointmentSuccessFragmentInterface.View> {
    }
}
