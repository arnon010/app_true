package com.truedigital.vhealth.ui.home.doctorstandby;

import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ListDoctorStandbyFragmentInterface {


    public interface View extends BaseMvpInterface.View {
        void openDoctorDetail(int doctorId);

        void openListDoctor(int catId);

        void setData(List<ItemDoctorDao> listData);

        String getDataFromFile(String filename);

        List<ItemDoctorDao> getData(String json, boolean isShowLog);

        int getCategoryId();

        int getClinicId();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListDoctorStandbyFragmentInterface.View> {
        void getListDoctors(int categoryId, int clinicId, int pageIndex, int pageSize);
    }
}
