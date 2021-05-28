package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiChartObject;
import com.truedigital.vhealth.model.ChartObject;
import com.truedigital.vhealth.model.GraphBloodPressureCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class GraphBloodPressureFragmentPresenter extends BaseMvpPresenter<GraphBloodPressureFragmentInterface.View>
        implements GraphBloodPressureFragmentInterface.Presenter{

    public static GraphBloodPressureFragmentInterface.Presenter create(){
        return new GraphBloodPressureFragmentPresenter();
    }

    @Override
    public void getGraphBloodPressure(GraphBloodPressureCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiChartObject> call = getRetrofitToken(access_token).getChartDataBloodPressure(criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getType(), criteria.getPeriod());
        call.enqueue(new Callback<ApiChartObject>() {
            @Override
            public void onResponse(Call<ApiChartObject> call, Response<ApiChartObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ChartObject data = response.body().getData();
                    getView().setGraphBloodPressure(data);

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
            public void onFailure(Call<ApiChartObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
