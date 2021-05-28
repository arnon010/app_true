package com.truedigital.vhealth.ui.home.category;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemCategoryDao;
import com.truedigital.vhealth.model.ListCategoryDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListCategoryFragmentPresenter extends BaseMvpPresenter<ListCategoryFragmentInterface.View>
        implements ListCategoryFragmentInterface.Presenter {

    private List<ItemCategoryDao> listData;

    public static ListCategoryFragmentInterface.Presenter create() {
        return new ListCategoryFragmentPresenter();
    }

    @Override
    public void loadData() {
        callGetListCategory();
    }

    private void callGetListCategory() {
        if (listData != null && !listData.isEmpty()) {
            getView().setData(listData);
            return;
        }

        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        getView().showLoading();
        Call<ListCategoryDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListCategories(true, true);
        call.enqueue(new Callback<ListCategoryDao>() {
            @Override
            public void onResponse(Call<ListCategoryDao> call, Response<ListCategoryDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    listData = response.body().getListData();
                    getView().setData(listData);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListCategoryDao> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
    }
}
