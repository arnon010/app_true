package com.truedigital.vhealth.ui.articles.similar;

import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ArticlesSimilarFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        int getArticleId();
        void setData(List<ItemArticleDao> listData);
        void openDetail(int articleId);
        void openDetail(ItemArticleDao data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ArticlesSimilarFragmentInterface.View>{
        void loadData();
        void getArticlesSimilar(int articleId);
    }
}
