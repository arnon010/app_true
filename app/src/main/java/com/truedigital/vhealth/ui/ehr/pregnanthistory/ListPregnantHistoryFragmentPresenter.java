package com.truedigital.vhealth.ui.ehr.pregnanthistory;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListPregnantHistoryObject;
import com.truedigital.vhealth.model.ApiPregnantHistoryObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.PregnantHistoryObject;
import com.truedigital.vhealth.model.PregnantHistoryCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListPregnantHistoryFragmentPresenter extends BaseMvpPresenter<ListPregnantHistoryFragmentInterface.View>
        implements ListPregnantHistoryFragmentInterface.Presenter{

    public static ListPregnantHistoryFragmentInterface.Presenter create(){
        return new ListPregnantHistoryFragmentPresenter();
    }

    @Override
    public void getPregnantHistoryList(PregnantHistoryCriteriaObject criteria){
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListPregnantHistoryObject> call = getRetrofitToken(access_token).getPregnantList(criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getPlace(), criteria.getStartDateString(), criteria.getEndDateString(), criteria.getPageIndex(), criteria.getPageSize());
        call.enqueue(new Callback<ApiListPregnantHistoryObject>() {
            @Override
            public void onResponse(Call<ApiListPregnantHistoryObject> call, Response<ApiListPregnantHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<PregnantHistoryObject> dataList = response.body().getDataList();
                    for (PregnantHistoryObject data : dataList) {

                        if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                            data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                        }
                    }

                    getView().setPregnantHistoryList(dataList);

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
            public void onFailure(Call<ApiListPregnantHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getPregnantHistoryDetail(PregnantHistoryObject criteria){
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiPregnantHistoryObject> call = getRetrofitToken(access_token).getPregnantHistoryDetail(criteria.getPatientId(), criteria.getPregnancyId());
        call.enqueue(new Callback<ApiPregnantHistoryObject>() {
            @Override
            public void onResponse(Call<ApiPregnantHistoryObject> call, Response<ApiPregnantHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    PregnantHistoryObject data = response.body().getData();

                    if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                        data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                    }

                    getView().setPregnantHistoryDetail(data);

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
            public void onFailure(Call<ApiPregnantHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void deletePregnantHistory(final PregnantHistoryObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deletePregnantHistory(data.getPregnancyId());
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
