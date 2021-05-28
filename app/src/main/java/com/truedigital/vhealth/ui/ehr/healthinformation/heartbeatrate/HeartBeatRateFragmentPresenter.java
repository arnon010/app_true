package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiTodayHeartRestingRateObject;
import com.truedigital.vhealth.model.TodayHeartRestingRateObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;


public class HeartBeatRateFragmentPresenter extends BaseMvpPresenter<HeartBeatRateFragmentInterface.View>
        implements HeartBeatRateFragmentInterface.Presenter {

    public static HeartBeatRateFragmentInterface.Presenter create() {
        return new HeartBeatRateFragmentPresenter();
    }

    @Override
    public void getTodayHeartRestingRate(int patientId, int patientMenuId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiTodayHeartRestingRateObject> call = getRetrofitToken(access_token).getTodayHeartRestingRate(patientId, patientMenuId);
        call.enqueue(new Callback<ApiTodayHeartRestingRateObject>() {
            @Override
            public void onResponse(Call<ApiTodayHeartRestingRateObject> call, Response<ApiTodayHeartRestingRateObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    TodayHeartRestingRateObject data = response.body().getData();

                    getView().setTodayHeartRestingRate(data);

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
            public void onFailure(Call<ApiTodayHeartRestingRateObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }


}
