package com.truedigital.vhealth.ui.articles;

import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ArticlesFragmentInterface {


    public interface View extends BaseMvpInterface.View{

        void openDetail(ItemArticleDao data);
        void openDetail(int articleId);
        void setData(List<ItemArticleDao> listData);

        int getDoctorId();
        String getDoctorName();
        int getArticleGroupId();

        void openWriteArticle();
        void openUploadClip();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ArticlesFragmentInterface.View>{
        void loadData();
    }
}
