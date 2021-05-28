package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListDailyHeartBeatRateObject;
import com.truedigital.vhealth.model.DailyHeartBeatRateObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListDailyHeartBeatRateFragmentPresenter extends BaseMvpPresenter<ListDailyHeartBeatRateFragmentInterface.View>
        implements ListDailyHeartBeatRateFragmentInterface.Presenter{

    public static ListDailyHeartBeatRateFragmentInterface.Presenter create(){
        return new ListDailyHeartBeatRateFragmentPresenter();
    }

    @Override
    public void getListDailyHeartBeatRate(int patientId, int patientMenuId){
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListDailyHeartBeatRateObject> call = getRetrofitToken(access_token).getDailyHeartBeatRate(patientId, patientMenuId);
        call.enqueue(new Callback<ApiListDailyHeartBeatRateObject>() {
            @Override
            public void onResponse(Call<ApiListDailyHeartBeatRateObject> call, Response<ApiListDailyHeartBeatRateObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<DailyHeartBeatRateObject> dataList = response.body().getDataList();

                    for (DailyHeartBeatRateObject data : dataList) {

                        if (data.getDailyDateString() != null && data.getDailyDateString() != "") {

                            data.setDailyDate(ConvertDate.StrToDateFormat(data.getDailyDateString()));
                        }
                    }

                    getView().setListDailyHeartBeatRate(dataList);

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
            public void onFailure(Call<ApiListDailyHeartBeatRateObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }


}
