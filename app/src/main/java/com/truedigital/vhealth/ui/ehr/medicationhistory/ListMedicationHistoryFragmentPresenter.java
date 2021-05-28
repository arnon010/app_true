package com.truedigital.vhealth.ui.ehr.medicationhistory;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListMedicationHistoryObject;
import com.truedigital.vhealth.model.ApiMedicationHistoryObject;
import com.truedigital.vhealth.model.MedicationHistoryObject;
import com.truedigital.vhealth.model.MedicationHistoryCriteriaObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListMedicationHistoryFragmentPresenter extends BaseMvpPresenter<ListMedicationHistoryFragmentInterface.View>
        implements ListMedicationHistoryFragmentInterface.Presenter{

    public static ListMedicationHistoryFragmentInterface.Presenter create(){
        return new ListMedicationHistoryFragmentPresenter();
    }

    @Override
    public void getMedicationHistoryList(MedicationHistoryCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListMedicationHistoryObject> call = getRetrofitToken(access_token).getMedicinationList(criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getPlace(), criteria.getStartDateString(), criteria.getEndDateString(), criteria.getPageIndex(), criteria.getPageSize());
        call.enqueue(new Callback<ApiListMedicationHistoryObject>() {
            @Override
            public void onResponse(Call<ApiListMedicationHistoryObject> call, Response<ApiListMedicationHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<MedicationHistoryObject> dataList = response.body().getDataList();
                    for (MedicationHistoryObject data : dataList) {

                        if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                            data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                        }
                    }

                    getView().setMedicationHistoryList(dataList);

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
            public void onFailure(Call<ApiListMedicationHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getMedicationHistoryDetail(MedicationHistoryObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiMedicationHistoryObject> call = getRetrofitToken(access_token).getMedicinationHistoryDetail(criteria.getPatientId(), criteria.getMedicinationId());
        call.enqueue(new Callback<ApiMedicationHistoryObject>() {
            @Override
            public void onResponse(Call<ApiMedicationHistoryObject> call, Response<ApiMedicationHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    MedicationHistoryObject data = response.body().getData();

                    if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                        data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                    }

                    getView().setMedicationHistoryDetail(data);

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
            public void onFailure(Call<ApiMedicationHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void deleteMedicationHistory(final MedicationHistoryObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteMedicinationHistory(data.getMedicinationId());
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    getView().onDeleteSuccess(data);

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
