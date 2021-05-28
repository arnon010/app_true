package com.truedigital.vhealth.ui.articles.detail;

import android.widget.ImageView;

import com.truedigital.vhealth.model.ItemArticleGroupDao;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ArticlesDetailFragmentInterface {


    public interface View extends BaseMvpInterface.View{

        void openDetail(String appointment_no);
        void openDetail(int position, ItemAppointmentDao data, ImageView shareView);
        void setData(ItemArticleGroupDao data);

        String getDataFromFile(String filename);
        List<ItemAppointmentDao> getData(String json, boolean isShowLog);

        int getPosition();


        String getName();
        String getDetail();
        int getArticleGroupId();

        void setDataArticleGroup(List<ItemArticleGroupDao> listData);

        void openWriteArticle();
        void openUploadClip();
        void showSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ArticlesDetailFragmentInterface.View>{
        void loadData();
        //ItemAppointmentDao loadData(int position);
        void loadArticleGroup();
        void createArticle();
    }
}
