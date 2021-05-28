package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiHeartBeatRateObject;
import com.truedigital.vhealth.model.ApiListHeartBeatRateObject;
import com.truedigital.vhealth.model.DailyHeartBeatRateObject;
import com.truedigital.vhealth.model.HeartBeatRateObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListTimeHeartBeatRateFragmentPresenter extends BaseMvpPresenter<ListTimeHeartBeatRateFragmentInterface.View>
        implements ListTimeHeartBeatRateFragmentInterface.Presenter {

    public static ListTimeHeartBeatRateFragmentInterface.Presenter create() {
        return new ListTimeHeartBeatRateFragmentPresenter();
    }

    @Override
    public void getListHeartBeatRate(DailyHeartBeatRateObject daily) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListHeartBeatRateObject> call = getRetrofitToken(access_token).getHeartBeatRateList(daily.getPatientId(), daily.getPatientMenuId(), daily.getDailyDateString());
        call.enqueue(new Callback<ApiListHeartBeatRateObject>() {
            @Override
            public void onResponse(Call<ApiListHeartBeatRateObject> call, Response<ApiListHeartBeatRateObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<HeartBeatRateObject> dataList = response.body().getDataList();

                    for (HeartBeatRateObject data : dataList) {

                        if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                            data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                        }
                    }

                    getView().setListHeartBeatRate(dataList);

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                }

                getView().hideLoading();
                getView().checkDataEmpty();
            }

            @Override
            public void onFailure(Call<ApiListHeartBeatRateObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getHeartBeatRate(int patientId, HeartBeatRateObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiHeartBeatRateObject> call = getRetrofitToken(access_token).getHeartBeatRate(patientId, data.getHeartRateId());
        call.enqueue(new Callback<ApiHeartBeatRateObject>() {
            @Override
            public void onResponse(Call<ApiHeartBeatRateObject> call, Response<ApiHeartBeatRateObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    HeartBeatRateObject data = response.body().getData();

                    if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                        data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                    }

                    getView().setHeartBeatRate(data);

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

}
