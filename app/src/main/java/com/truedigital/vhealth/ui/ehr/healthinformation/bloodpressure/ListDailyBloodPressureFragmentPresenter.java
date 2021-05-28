package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListDailyBloodPressureObject;
import com.truedigital.vhealth.model.DailyBloodPressureObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListDailyBloodPressureFragmentPresenter extends BaseMvpPresenter<ListDailyBloodPressureFragmentInterface.View>
        implements ListDailyBloodPressureFragmentInterface.Presenter{

    public static ListDailyBloodPressureFragmentInterface.Presenter create(){
        return new ListDailyBloodPressureFragmentPresenter();
    }

    @Override
    public void getListDailyBloodPressure(int patientId, int patientMenuId){
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListDailyBloodPressureObject> call = getRetrofitToken(access_token).getDailyBloodPressure(patientId, patientMenuId);
        call.enqueue(new Callback<ApiListDailyBloodPressureObject>() {
            @Override
            public void onResponse(Call<ApiListDailyBloodPressureObject> call, Response<ApiListDailyBloodPressureObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<DailyBloodPressureObject> dataList = response.body().getDataList();

                    for (DailyBloodPressureObject data : dataList) {

                        if (data.getDailyDateString() != null && data.getDailyDateString() != "") {

                            data.setDailyDate(ConvertDate.StrToDateFormat(data.getDailyDateString()));
                        }
                    }

                    getView().setListDailyBloodPressure(dataList);

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
            public void onFailure(Call<ApiListDailyBloodPressureObject> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }


}
