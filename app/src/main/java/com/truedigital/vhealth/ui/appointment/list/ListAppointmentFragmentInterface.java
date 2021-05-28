package com.truedigital.vhealth.ui.appointment.list;

import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ListAppointmentFragmentInterface {


    public interface View extends BaseMvpInterface.View {

        void openDetail(int appointmentType, String appointmentNumber);

        void setData(List<ItemAppointmentDao> listData);

        String getDataFromFile(String filename);

        List<ItemAppointmentDao> getData(String json, boolean isShowLog);

        int getSortOrder();

        int getAppointmentType();

        String getSortBy();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListAppointmentFragmentInterface.View> {
        void loadData();

        void loadData(int sortBy, int menu);
    }
}
