package com.truedigital.vhealth.ui.home.clinic;


import com.truedigital.vhealth.model.ItemClinicDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ListClinicFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        void openListDoctor(ItemClinicDao itemClinicDao);
        void setData(List<ItemClinicDao> listData);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListClinicFragmentInterface.View>{
        void loadData();
    }
}
