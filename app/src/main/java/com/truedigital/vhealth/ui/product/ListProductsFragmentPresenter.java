package com.truedigital.vhealth.ui.product;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.ListProductDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListProductsFragmentPresenter extends BaseMvpPresenter<ListProductsFragmentInterface.View>
        implements ListProductsFragmentInterface.Presenter{

    private List<ItemProductDao> listData;

    public static ListProductsFragmentInterface.Presenter create(){
        return new ListProductsFragmentPresenter();
    }

    @Override
    public void loadData() {
        //setTempData();
        //
        callGetListProducts(getView().getProductGroupId());
    }

    private void callGetListProducts(int productGroupId) {
        getView().showLoading();

        Call<ListProductDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListProducts(productGroupId, true);
        call.enqueue(new Callback<ListProductDao>() {
            @Override
            public void onResponse(Call<ListProductDao> call, Response<ListProductDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {

                    getView().setData(response.body().getListData());
                    //getView().setData(listData);
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListProductDao> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

    private void setTempData() {
        String json = getView().getDataFromFile("seed/products.json");
        listData = getView().getData(json, true);
    }


}
