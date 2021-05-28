package com.truedigital.vhealth.ui.articles.patient;

import android.widget.ImageView;

import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.model.ItemArticleGroupDao;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ArticlesPatientDetailFragmentInterface {


    public interface View extends BaseMvpInterface.View{

        void openDetail(String appointment_no);
        void openDetail(int position, ItemAppointmentDao data, ImageView shareView);
        void setData(ItemArticleDao data);

        String getDataFromFile(String filename);
        List<ItemAppointmentDao> getData(String json, boolean isShowLog);

        int getPosition();

        String getDoctorName();
        String getName();
        String getDetail();
        int getArticleGroupId();
        int getArticleId();

        void setDataArticleGroup(List<ItemArticleGroupDao> listData);

        void openAppointment();
        void openWriteArticle();
        void openUploadClip();
        void showSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ArticlesPatientDetailFragmentInterface.View>{
        void loadData();
        //ItemAppointmentDao loadData(int position);
        void loadArticleGroup();
        void createArticle();
        void getArticlesById(int id);
    }
}
