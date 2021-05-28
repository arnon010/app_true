package com.truedigital.vhealth.ui.home.searchdoctor;


import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class SearchDoctorFragmentInterface {


    public interface View extends BaseMvpInterface.View{

        void setFavorite(int id, boolean isFavorite);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<SearchDoctorFragmentInterface.View>{
        void loadDoctor();
        void callApiAddFavorite(final int id);
        void callApiDelFavorite(final int id);
    }
}
