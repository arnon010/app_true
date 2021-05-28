package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiHeartBeatRateObject;
import com.truedigital.vhealth.model.ApiListHeartBeatRateTypeObject;
import com.truedigital.vhealth.model.HeartBeatRateObject;
import com.truedigital.vhealth.model.HeartBeatRateTypeObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class FormHeartBeatRateFragmentPresenter extends BaseMvpPresenter<FormHeartBeatRateFragmentInterface.View>
        implements FormHeartBeatRateFragmentInterface.Presenter{

    public static FormHeartBeatRateFragmentInterface.Presenter create(){
        return new FormHeartBeatRateFragmentPresenter();
    }

    @Override
    public void getTypeList() {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListHeartBeatRateTypeObject> call = getRetrofitToken(access_token).getHeartBeatRateType();
        call.enqueue(new Callback<ApiListHeartBeatRateTypeObject>() {
            @Override
            public void onResponse(Call<ApiListHeartBeatRateTypeObject> call, Response<ApiListHeartBeatRateTypeObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<HeartBeatRateTypeObject> dataList = response.body().getDataList();

                    getView().setTypeList(dataList);

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
            public void onFailure(Call<ApiListHeartBeatRateTypeObject> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

    @Override
    public void newHeartBeatRate(HeartBeatRateObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiHeartBeatRateObject> call = getRetrofitToken(access_token).postHeartBeatRate(data.getPatientId(), data.getPatientMenuId(), data.getMenuCode(), data.getType(), data.getRecordDateString(), data.getBPM());
        call.enqueue(new Callback<ApiHeartBeatRateObject>() {
            @Override
            public void onResponse(Call<ApiHeartBeatRateObject> call, Response<ApiHeartBeatRateObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    HeartBeatRateObject data = response.body().getData();

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
            public void onFailure(Call<ApiHeartBeatRateObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void updateHeartBeatRate(HeartBeatRateObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).putHeartBeatRate(data.getPatientId(), data.getHeartRateId(), data.getType(), data.getRecordDateString(), data.getBPM());
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
    public void deleteHeartBeatRate(HeartBeatRateObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteHeartBeatRate(data.getHeartRateId());
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
