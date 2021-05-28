package com.truedigital.vhealth.ui.home.patient;

import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.model.ItemProductBannerDao;
import com.truedigital.vhealth.model.ItemSubCategoryDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class PatientHomeFragmentInterface {

    public interface View extends BaseMvpInterface.View {

        void openProductDetail(int id);

        void setDataHealthcare(List<ItemSubCategoryDao> listData);

        void setDataBannerProduct(List<ItemProductBannerDao> listData);

        void openListProducts(int groupId);

        void setDataBannerArticle(List<ItemArticleDao> listData);

        void openArticleDetail(int id);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<PatientHomeFragmentInterface.View> {

        void loadListHealthcare();

        void loadDataPromotion();

        void loadDataArticle();
    }
}
