package com.truedigital.vhealth.ui.home.doctor;

import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ListDoctorFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        void openDoctorDetail(int doctorId);
        void openListDoctor(int catId);
        void updateDoctor(List<ItemDoctorDao> listData);
        void setData(List<ItemDoctorDao> listData);

        int getCategoryId();
        int getClinicId();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListDoctorFragmentInterface.View>{
        void getListDoctors(int categoryId, int clinicId, int pageIndex, int pageSize);
        void getListDoctorsFromSubCategoryId(int subCategoryId, int pageIndex, int pageSize);
    }
}
