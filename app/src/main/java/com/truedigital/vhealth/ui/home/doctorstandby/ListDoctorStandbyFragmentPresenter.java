package com.truedigital.vhealth.ui.home.doctorstandby;

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

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListDoctorStandbyFragmentPresenter extends BaseMvpPresenter<ListDoctorStandbyFragmentInterface.View>
        implements ListDoctorStandbyFragmentInterface.Presenter {

    private List<ItemDoctorDao> listData;

    public static ListDoctorStandbyFragmentInterface.Presenter create() {
        return new ListDoctorStandbyFragmentPresenter();
    }

    @Override
    public void getListDoctors(final int categoryId, final int clinicId, final int pageIndex, final int pageSize) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        getView().showLoading();
        Call<ApiListDoctor> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListDoctorsStandby(true, pageIndex, pageSize);
        call.enqueue(new Callback<ApiListDoctor>() {
            @Override
            public void onResponse(Call<ApiListDoctor> call, Response<ApiListDoctor> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setData(response.body().getListData());
                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        refresh(categoryId, clinicId, pageIndex, pageSize);
                    } else {
                        getView().showMessage(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiListDoctor> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(t.getMessage());
            }
        });
    }

    private void refresh(final int categoryId, final int clinicId, final int pageIndex, final int pageSize) {
        onRefresh_token(AppManager.getDataManager().getRefresh_token(), new OnRefreshListener() {
            @Override
            public void onSuccess() {
                getListDoctors(categoryId, clinicId, pageIndex, pageSize);
            }

            @Override
            public void onFail() {
            }
        });
    }
}
