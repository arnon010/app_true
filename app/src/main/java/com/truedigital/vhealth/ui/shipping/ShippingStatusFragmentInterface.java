package com.truedigital.vhealth.ui.shipping;


import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class ShippingStatusFragmentInterface {


    public interface View extends BaseMvpInterface.View{

        void updateStatus(String status);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ShippingStatusFragmentInterface.View>{
        void getShippingStatus(String appointmentNumber);
    }
}
