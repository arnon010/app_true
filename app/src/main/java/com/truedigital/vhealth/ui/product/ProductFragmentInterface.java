package com.truedigital.vhealth.ui.product;


import com.truedigital.vhealth.model.ItemProductBannerDao;
import com.truedigital.vhealth.model.ItemProductGroupDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ProductFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        void setData(List<ItemProductGroupDao> listData);
        void openListProducts(int groupId);
        void openProductDetail(int id);
        void setDataBanner(List<ItemProductBannerDao> listData);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ProductFragmentInterface.View>{
        void loadData();
    }
}
