package com.truedigital.vhealth.ui.ehr.medicationhistory;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListFromMedicationHistoryCriteriaObject;
import com.truedigital.vhealth.model.ApiMedicationHistoryCriteriaObject;
import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.MedicationHistoryCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class MedicationHistoryFragmentPresenter extends BaseMvpPresenter<MedicationHistoryFragmentInterface.View>
        implements MedicationHistoryFragmentInterface.Presenter{

    public static MedicationHistoryFragmentInterface.Presenter create(){
        return new MedicationHistoryFragmentPresenter();
    }

    @Override
    public void getMedicationFromSelectList(int patientId, int patientMenuId){
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListFromMedicationHistoryCriteriaObject> call = getRetrofitToken(access_token).getMedicinationPlaceListCriteria(patientId, patientMenuId);
        call.enqueue(new Callback<ApiListFromMedicationHistoryCriteriaObject>() {
            @Override
            public void onResponse(Call<ApiListFromMedicationHistoryCriteriaObject> call, Response<ApiListFromMedicationHistoryCriteriaObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<String> criteriaList = response.body().getCriteriaList();
                    ArrayList<BottomSheetObject> dataList = new ArrayList<>();

                    for (String criteria : criteriaList) {

                        BottomSheetObject data = new BottomSheetObject();
                        data.setDescriptions(criteria);
                        data.setValueItem(criteria);
                        dataList.add(data);
                    }

                    getView().setMedicationFromSelectListCriteria(dataList);

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
            public void onFailure(Call<ApiListFromMedicationHistoryCriteriaObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getDefaultDateCriteria(int patientId, int patientMenuId) {
        getView().showLoading();

        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiMedicationHistoryCriteriaObject> call = getRetrofitToken(access_token).getMedicinationDefaultDateCriteria(patientId, patientMenuId);
        call.enqueue(new Callback<ApiMedicationHistoryCriteriaObject>() {
            @Override
            public void onResponse(Call<ApiMedicationHistoryCriteriaObject> call, Response<ApiMedicationHistoryCriteriaObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    MedicationHistoryCriteriaObject criteria = response.body().getCriteria();

                    if (criteria.getStartDateString() != null && criteria.getStartDateString() != "") {
                        criteria.setStartDate(ConvertDate.StrToDateFormat(criteria.getStartDateString()));
                    }

                    if (criteria.getEndDateString() != null && criteria.getEndDateString() != "") {
                        criteria.setEndDate(ConvertDate.StrToDateFormat(criteria.getEndDateString()));
                    }

                    if (criteria.getStartDate() == null) {
                        criteria.setStartDate(new Date());
                    }

                    if (criteria.getEndDate() == null) {
                        criteria.setEndDate(new Date());
                    }

                    getView().setDefaultDateCriteria(criteria);

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
            public void onFailure(Call<ApiMedicationHistoryCriteriaObject> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

}
