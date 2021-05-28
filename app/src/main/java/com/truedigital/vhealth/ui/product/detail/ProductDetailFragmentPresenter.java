package com.truedigital.vhealth.ui.product.detail;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ProductDetailFragmentPresenter extends BaseMvpPresenter<ProductDetailFragmentInterface.View>
        implements ProductDetailFragmentInterface.Presenter {

    private ItemProductDao itemProductDao;
    private int mProductId;

    public static ProductDetailFragmentInterface.Presenter create() {
        return new ProductDetailFragmentPresenter();
    }

    @Override
    public void loadData() {
        callGetProduct(getView().getProductId());
    }

    @Override
    public void onBuyButtonClick() {
        int id = getView().getProductId();
        getView().openProductConfirm(id);
    }

    private void callGetProduct(int productId) {
        if (mProductId == productId && itemProductDao != null) {
            getView().setData(itemProductDao);
            return;
        }

        getView().showLoading();

        Call<ItemProductDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getProduct(productId);
        call.enqueue(new Callback<ItemProductDao>() {
            @Override
            public void onResponse(Call<ItemProductDao> call, Response<ItemProductDao> response) {
                getView().hideLoading();

                if (response.isSuccessful()) {
                    itemProductDao = response.body();
                    mProductId = productId;
                    getView().setData(itemProductDao);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ItemProductDao> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
