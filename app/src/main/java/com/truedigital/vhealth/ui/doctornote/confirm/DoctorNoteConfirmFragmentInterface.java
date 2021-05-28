package com.truedigital.vhealth.ui.doctornote.confirm;


import com.truedigital.vhealth.model.ItemMedicineDao;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.appointment.ItemAppointmentShortnoteDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class DoctorNoteConfirmFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        String getAppointmentNumber();
        String getReason();

        void openConfirm();
        void showSuccess();
        void onSuccess();
        void setData(ItemAppointmentShortnoteDao data);
        void setDataProduct(List<ItemProductDao> listData);
        void setDataMedicine(List<ItemMedicineDao> listData);
        void updateView();
        void updateViewPayment();
        void openProductDetail(int id);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<DoctorNoteConfirmFragmentInterface.View>{
        void loadData();
        void getAppointmentShortnote(final String appointmentNumber);
        void updateAppointmentShortnote(final String appointmentNumber, String reason);
        void getAppointmentRecommendProduct(String appointmentNumber);
        void getAppointmentRecommendMedicine(String appointmentNumber);

        void sendDoctorNote(final String appointmentNumber);
    }
}
