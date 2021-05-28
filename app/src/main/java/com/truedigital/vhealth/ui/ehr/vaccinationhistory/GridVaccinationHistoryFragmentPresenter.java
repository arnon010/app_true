package com.truedigital.vhealth.ui.ehr.vaccinationhistory;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiGridVaccinationHistoryObject;
import com.truedigital.vhealth.model.ApiVaccinationHistoryObject;
import com.truedigital.vhealth.model.ColumnHeaderVaccineObject;
import com.truedigital.vhealth.model.GridVaccinationHistoryCriteriaObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.VaccinationHistoryObject;
import com.truedigital.vhealth.model.RowHeaderVaccineObject;
import com.truedigital.vhealth.model.GridVaccinationHistoryObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class GridVaccinationHistoryFragmentPresenter extends BaseMvpPresenter<GridVaccinationHistoryFragmentInterface.View>
        implements GridVaccinationHistoryFragmentInterface.Presenter {

    public static GridVaccinationHistoryFragmentInterface.Presenter create() {
        return new GridVaccinationHistoryFragmentPresenter();
    }

    @Override
    public void getGridDataVaccinationHistory(GridVaccinationHistoryCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiGridVaccinationHistoryObject> call = getRetrofitToken(access_token).getGridVaccinationHistory(criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getIsNecessary());
        call.enqueue(new Callback<ApiGridVaccinationHistoryObject>() {
            @Override
            public void onResponse(Call<ApiGridVaccinationHistoryObject> call, Response<ApiGridVaccinationHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    GridVaccinationHistoryObject data = response.body().getData();
                    if (data != null) {
                        data.setColumnHeaderListTemp(new ArrayList<String>());

                        for (RowHeaderVaccineObject row : data.getRowHeaderList()) {
                            row.setText(row.getName());
                        }

                        for (ColumnHeaderVaccineObject column : data.getColumnHeaderList()) {
                            data.getColumnHeaderListTemp().add(column.getMonthDescription());
                        }
                    }

                    getView().setGridDataVaccinationHistory(data);

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
            public void onFailure(Call<ApiGridVaccinationHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void activeDataVaccinationHistory(VaccinationHistoryObject data, int patientId, int patientMenuId, String menuCode) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiVaccinationHistoryObject> call = getRetrofitToken(access_token).postNewVaccinationHistory(patientId, patientMenuId, menuCode, data.getVaccineId(), data.getMonth());
        call.enqueue(new Callback<ApiVaccinationHistoryObject>() {
            @Override
            public void onResponse(Call<ApiVaccinationHistoryObject> call, Response<ApiVaccinationHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    VaccinationHistoryObject data = response.body().getData();
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
            public void onFailure(Call<ApiVaccinationHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void inActiveDataVaccinationHistory(int patientVaccinationId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteVaccinationHistory(patientVaccinationId);
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
