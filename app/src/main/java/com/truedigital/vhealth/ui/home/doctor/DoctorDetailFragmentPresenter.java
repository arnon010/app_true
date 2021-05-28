package com.truedigital.vhealth.ui.home.doctor;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.DoctorFavoriteRequest;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class DoctorDetailFragmentPresenter extends BaseMvpPresenter<DoctorDetailFragmentInterface.View>
        implements DoctorDetailFragmentInterface.Presenter {

    private ItemDoctorDao itemDoctorDao;

    public static DoctorDetailFragmentInterface.Presenter create() {
        return new DoctorDetailFragmentPresenter();
    }

    @Override
    public void loadData() {
        callGetDoctor(getView().getDoctorId());
    }

    private void callGetDoctor(final int doctorId) {
        if (itemDoctorDao != null) {
            getView().setData(itemDoctorDao);
            return;
        }

        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }
        getView().showLoading();
        Call<ItemDoctorDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getDoctor(doctorId);
        call.enqueue(new Callback<ItemDoctorDao>() {
            @Override
            public void onResponse(Call<ItemDoctorDao> call, Response<ItemDoctorDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    itemDoctorDao = response.body();
                    getView().setData(itemDoctorDao);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ItemDoctorDao> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
    }

    @Override
    public void onFavoriteClick(int doctorId, boolean isDoctorFavorite) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        getView().showLoading();

        DoctorFavoriteRequest request = new DoctorFavoriteRequest();
        final boolean toggleDoctorFavorite = !isDoctorFavorite;
        request.setFavorite(toggleDoctorFavorite);

        Call<NormalResponseObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postDoctorFavorite(doctorId, request);

        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setDoctorFavorite(toggleDoctorFavorite);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
