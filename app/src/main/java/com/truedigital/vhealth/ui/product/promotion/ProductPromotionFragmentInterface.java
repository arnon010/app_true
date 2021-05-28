package com.truedigital.vhealth.ui.product.promotion;

import com.truedigital.vhealth.model.ItemProductBannerDao;
import com.truedigital.vhealth.model.ItemProductGroupDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ProductPromotionFragmentInterface {

    public interface View extends BaseMvpInterface.View {
        void setDataBanner(List<ItemProductBannerDao> listData);

        void setData(List<ItemProductGroupDao> listData);

        void openListProducts(int groupId);

        void openProductDetail(int id);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ProductPromotionFragmentInterface.View> {
        void loadData();
    }
}
