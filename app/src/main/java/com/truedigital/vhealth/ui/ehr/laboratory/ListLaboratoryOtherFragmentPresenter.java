package com.truedigital.vhealth.ui.ehr.laboratory;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiLaboratoryOtherObject;
import com.truedigital.vhealth.model.ApiListLaboratoryOtherObject;
import com.truedigital.vhealth.model.LaboratoryOtherObject;
import com.truedigital.vhealth.model.LaboratoryOtherCriteriaObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListLaboratoryOtherFragmentPresenter extends BaseMvpPresenter<ListLaboratoryOtherFragmentInterface.View>
        implements ListLaboratoryOtherFragmentInterface.Presenter {

    public static ListLaboratoryOtherFragmentInterface.Presenter create() {
        return new ListLaboratoryOtherFragmentPresenter();
    }

    public void getLaboratoryOtherList(LaboratoryOtherCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListLaboratoryOtherObject> call = getRetrofitToken(access_token).getPatientManualLabList(criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getPageIndex(), criteria.getPageSize());
        call.enqueue(new Callback<ApiListLaboratoryOtherObject>() {
            @Override
            public void onResponse(Call<ApiListLaboratoryOtherObject> call, Response<ApiListLaboratoryOtherObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<LaboratoryOtherObject> dataList = response.body().getDataList();
                    for (LaboratoryOtherObject data : dataList) {

                        if (data.getLabDateString() != null && data.getLabDateString() != "") {

                            data.setLabDate(ConvertDate.StrToDateFormat(data.getLabDateString()));
                        }
                    }

                    getView().setLaboratoryOtherList(dataList);

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
            public void onFailure(Call<ApiListLaboratoryOtherObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    public void getLaboratoryOtherDetail(LaboratoryOtherObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiLaboratoryOtherObject> call = getRetrofitToken(access_token).getPatientManualLabDetail(criteria.getPatientId(), criteria.getLabId());
        call.enqueue(new Callback<ApiLaboratoryOtherObject>() {
            @Override
            public void onResponse(Call<ApiLaboratoryOtherObject> call, Response<ApiLaboratoryOtherObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    LaboratoryOtherObject data = response.body().getData();

                    if (data.getLabDateString() != null && data.getLabDateString() != "") {

                        data.setLabDate(ConvertDate.StrToDateFormat(data.getLabDateString()));
                    }

                    getView().setLaboratoryOtherDetail(data);

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
            public void onFailure(Call<ApiLaboratoryOtherObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void deleteLaboratoryOther(final LaboratoryOtherObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deletePatientManualLab(data.getLabId());
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
