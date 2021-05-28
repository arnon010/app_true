package com.truedigital.vhealth.ui.articles.carousel;

import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ArticlesCarouselFragmentInterface {

    public interface View extends BaseMvpInterface.View {
        void setDataBanner(List<ItemArticleDao> listData);

        void setData(List<ItemArticleDao> listData);

        void openListProducts(int groupId);

        void openArticleDetail(int id);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ArticlesCarouselFragmentInterface.View> {
        void loadData();
    }
}
