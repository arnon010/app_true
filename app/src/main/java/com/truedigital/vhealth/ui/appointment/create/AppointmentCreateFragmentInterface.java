package com.truedigital.vhealth.ui.appointment.create;

import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.ItemDoctorScheduleTimeDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class AppointmentCreateFragmentInterface {


    public interface View extends BaseMvpInterface.View {

        int getPatientId();

        int getDoctorId();

        boolean isValid();

        void openPersonConsult();

        void openAppointmentCreateDetail(ItemDoctorDao data);

        void setDoctorAvailableTimes(List<ItemDoctorScheduleTimeDao> data);

        void setTimeSlot(String timeSlot);

        void setChannel(String channelCode);

        void openDoctorCalendar(int doctorId);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<AppointmentCreateFragmentInterface.View> {
        void loadData();
    }
}
