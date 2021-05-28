package com.truedigital.vhealth.ui.home.doctor;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListDoctor;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListDoctorFragmentPresenter extends BaseMvpPresenter<ListDoctorFragmentInterface.View>
        implements ListDoctorFragmentInterface.Presenter {

    private List<ItemDoctorDao> listData;

    public static ListDoctorFragmentInterface.Presenter create() {
        return new ListDoctorFragmentPresenter();
    }

    private void delayLoading(Runnable runnable) {
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void getListDoctors(final int categoryId, final int clinicId, final int pageIndex, final int pageSize) {

        if (listData != null && !listData.isEmpty()) {
            getView().setData(listData);
            return;
        }

        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        Timber.e("Cat " + categoryId + " clicnic " + clinicId);
        getView().showLoading();
        Call<ApiListDoctor> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListDoctors(categoryId, clinicId, pageIndex, pageSize);
        call.enqueue(new Callback<ApiListDoctor>() {
            @Override
            public void onResponse(Call<ApiListDoctor> call, Response<ApiListDoctor> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    listData = response.body().getListData();
                    getView().setData(listData);
                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        Timber.e("expire %s", AppManager.getDataManager().getAccess_token());
                        refresh(categoryId, clinicId, pageIndex, pageSize);
                    } else {
                        getView().showMessage(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiListDoctor> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
    }

    @Override
    public void getListDoctorsFromSubCategoryId(int subCategoryId, int pageIndex, int pageSize) {
        if (listData != null && !listData.isEmpty()) {
            getView().setData(listData);
            return;
        }

        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        getView().showLoading();
        Call<ApiListDoctor> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListDoctorsWithSubcategory(5445, subCategoryId, pageIndex, pageSize);
        call.enqueue(new Callback<ApiListDoctor>() {
            @Override
            public void onResponse(Call<ApiListDoctor> call, Response<ApiListDoctor> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    listData = response.body().getListData();
                    getView().setData(listData);
                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        Timber.e("expire %s", AppManager.getDataManager().getAccess_token());
                        refresh(subCategoryId, pageIndex, pageSize);
                    } else {
                        getView().showMessage(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiListDoctor> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
    }

    private void refresh(final int categoryId, final int clinicId, final int pageIndex, final int pageSize) {
        Timber.e("refresh %s", AppManager.getDataManager().getRefresh_token());
        onRefresh_token(AppManager.getDataManager().getRefresh_token(), new BaseMvpPresenter.OnRefreshListener() {
            @Override
            public void onSuccess() {
                Timber.e("success %s", AppManager.getDataManager().getAccess_token());
                getListDoctors(categoryId, clinicId, pageIndex, pageSize);
            }

            @Override
            public void onFail() {
            }
        });
    }

    private void refresh(final int subCategoryId, final int pageIndex, final int pageSize) {
        Timber.e("refresh %s", AppManager.getDataManager().getRefresh_token());
        onRefresh_token(AppManager.getDataManager().getRefresh_token(), new BaseMvpPresenter.OnRefreshListener() {
            @Override
            public void onSuccess() {
                Timber.e("success %s", AppManager.getDataManager().getAccess_token());
                getListDoctorsFromSubCategoryId(subCategoryId, pageIndex, pageSize);
            }

            @Override
            public void onFail() {
            }
        });
    }
}
