package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiChartObject;
import com.truedigital.vhealth.model.ChartObject;
import com.truedigital.vhealth.model.GraphBloodSugarCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class GraphBloodSugarFragmentPresenter extends BaseMvpPresenter<GraphBloodSugarFragmentInterface.View>
        implements GraphBloodSugarFragmentInterface.Presenter{

    public static GraphBloodSugarFragmentInterface.Presenter create(){
        return new GraphBloodSugarFragmentPresenter();
    }

    @Override
    public void getGraphBloodSugar(GraphBloodSugarCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiChartObject> call = getRetrofitToken(access_token).getChartDataBloodSugar(criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getType(), criteria.getPeriod());
        call.enqueue(new Callback<ApiChartObject>() {
            @Override
            public void onResponse(Call<ApiChartObject> call, Response<ApiChartObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ChartObject data = response.body().getData();
                    getView().setGraphBloodSugar(data);

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
