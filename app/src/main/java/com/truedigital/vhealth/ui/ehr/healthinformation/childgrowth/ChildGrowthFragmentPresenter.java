package com.truedigital.vhealth.ui.ehr.healthinformation.childgrowth;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiChildGrowthHistoryObject;
import com.truedigital.vhealth.model.ApiGridChildGrowthHistoryObject;
import com.truedigital.vhealth.model.ChildGrowthCriteriaObject;
import com.truedigital.vhealth.model.ChildGrowthHistoryObject;
import com.truedigital.vhealth.model.ColumnHeaderChildGrowthObject;
import com.truedigital.vhealth.model.GridChildGrowthHistoryObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.RowHeaderChildGrowthObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ChildGrowthFragmentPresenter extends BaseMvpPresenter<ChildGrowthFragmentInterface.View>
        implements ChildGrowthFragmentInterface.Presenter {

    public static ChildGrowthFragmentInterface.Presenter create() {
        return new ChildGrowthFragmentPresenter();
    }

    @Override
    public void getGridDataChildGrowthHistory(ChildGrowthCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiGridChildGrowthHistoryObject> call = getRetrofitToken(access_token).getGridChildGrowthHistory(criteria.getPatientId(), criteria.getPatientMenuId());
        call.enqueue(new Callback<ApiGridChildGrowthHistoryObject>() {
            @Override
            public void onResponse(Call<ApiGridChildGrowthHistoryObject> call, Response<ApiGridChildGrowthHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    GridChildGrowthHistoryObject data = response.body().getData();
                    if (data != null) {
                        data.setColumnHeaderListTemp(new ArrayList<String>());

                        for (RowHeaderChildGrowthObject row : data.getRowHeaderList()) {
                            row.setText(row.getMonthDisplay());
                        }

                        for (ColumnHeaderChildGrowthObject column : data.getColumnHeaderList()) {
                            data.getColumnHeaderListTemp().add(column.getCategoryName());
                        }
                    }

                    getView().setGridDataChildGrowthHistory(data);

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
            public void onFailure(Call<ApiGridChildGrowthHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void activeDataChildGrowthHistory(ChildGrowthHistoryObject data, int patientId, int patientMenuId, String menuCode) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiChildGrowthHistoryObject> call = getRetrofitToken(access_token).postNewChildGrowthHistory(patientId, patientMenuId, menuCode, data.getChildGrowthId());
        call.enqueue(new Callback<ApiChildGrowthHistoryObject>() {
            @Override
            public void onResponse(Call<ApiChildGrowthHistoryObject> call, Response<ApiChildGrowthHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ChildGrowthHistoryObject data = response.body().getData();
                    getView().onActiveSuccess(data);

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }

                    getView().hideLoading();
                }
            }

            @Override
            public void onFailure(Call<ApiChildGrowthHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void inActiveDataChildGrowthHistory(int patientChildGrowthId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteChildGrowthHistory(patientChildGrowthId);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    getView().onInActiveSuccess();

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }

                    getView().hideLoading();
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }


}
