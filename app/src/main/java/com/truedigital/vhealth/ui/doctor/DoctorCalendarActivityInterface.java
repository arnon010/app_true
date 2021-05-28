package com.truedigital.vhealth.ui.doctor;

import com.truedigital.vhealth.model.ItemTimeSlotDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class DoctorCalendarActivityInterface {
    public interface View extends BaseMvpInterface.View {
        int getDoctorId();
        String getSelectDate();
        void setDoctorCalendar();
        void setDataTimeSlot(List<ItemTimeSlotDao> listData);
    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void loadTimeSlot();
    }
}
