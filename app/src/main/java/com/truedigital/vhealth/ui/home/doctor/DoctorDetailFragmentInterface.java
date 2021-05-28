package com.truedigital.vhealth.ui.home.doctor;

import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class DoctorDetailFragmentInterface {


    public interface View extends BaseMvpInterface.View {
        void openAppointmentCreate(ItemDoctorDao data);

        int getDoctorId();

        void setData(ItemDoctorDao data);

        void setDoctorFavorite(boolean isDoctorFavorite);

        boolean isDoctorFavorite();

        void updateFavorite(boolean isDoctorFavorite);

        void openListArticles();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<DoctorDetailFragmentInterface.View> {
        void loadData();

        void onFavoriteClick(int doctorId, boolean isDoctorFavorite);
    }
}
