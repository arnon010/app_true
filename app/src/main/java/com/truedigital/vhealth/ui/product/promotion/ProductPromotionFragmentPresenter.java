package com.truedigital.vhealth.ui.product.promotion;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemProductBannerDao;
import com.truedigital.vhealth.model.ListProductBannerDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ProductPromotionFragmentPresenter extends BaseMvpPresenter<ProductPromotionFragmentInterface.View>
        implements ProductPromotionFragmentInterface.Presenter {

    private List<ItemProductBannerDao> listData;

    public static ProductPromotionFragmentInterface.Presenter create() {
        return new ProductPromotionFragmentPresenter();
    }

    @Override
    public void loadData() {
        callGetListProductBanner();
    }

    private void callGetListProductBanner() {
        if (listData != null && !listData.isEmpty()) {
            getView().setDataBanner(listData);
            return;
        }

        getView().showLoading();
        Call<ListProductBannerDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListProductBanner();
        call.enqueue(new Callback<ListProductBannerDao>() {
            @Override
            public void onResponse(Call<ListProductBannerDao> call, Response<ListProductBannerDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    listData = response.body().getListData();
                    getView().setDataBanner(listData);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListProductBannerDao> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
