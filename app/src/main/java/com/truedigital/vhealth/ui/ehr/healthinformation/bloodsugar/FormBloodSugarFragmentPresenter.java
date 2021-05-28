package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiBloodSugarObject;
import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class FormBloodSugarFragmentPresenter extends BaseMvpPresenter<FormBloodSugarFragmentInterface.View>
        implements FormBloodSugarFragmentInterface.Presenter {

    public static FormBloodSugarFragmentInterface.Presenter create() {
        return new FormBloodSugarFragmentPresenter();
    }

    @Override
    public void newBloodSugar(BloodSugarObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiBloodSugarObject> call = getRetrofitToken(access_token).postBloodSugar(data.getPatientId(), data.getPatientMenuId(), data.getMenuCode(), data.getRecordDateString(), data.getSugarValue());
        call.enqueue(new Callback<ApiBloodSugarObject>() {
            @Override
            public void onResponse(Call<ApiBloodSugarObject> call, Response<ApiBloodSugarObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    BloodSugarObject data = response.body().getData();

                    getView().onNewSuccess(data);

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiBloodSugarObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void updateBloodSugar(BloodSugarObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).putBloodSugar(data.getPatientId(), data.getBloodSugarId(), data.getRecordDateString(), data.getSugarValue());
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    getView().onUpdateSuccess();

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void deleteBloodSugar(BloodSugarObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteBloodSugar(data.getBloodSugarId());
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    getView().onDeleteSuccess();

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
