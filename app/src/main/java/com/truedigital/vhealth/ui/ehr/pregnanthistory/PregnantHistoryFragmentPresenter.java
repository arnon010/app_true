package com.truedigital.vhealth.ui.ehr.pregnanthistory;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiEhrMenuObject;
import com.truedigital.vhealth.model.ApiListPlacePregnantHistoryCriteriaObject;
import com.truedigital.vhealth.model.ApiPregnantHistoryCriteriaObject;
import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.model.PregnantHistoryCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class PregnantHistoryFragmentPresenter extends BaseMvpPresenter<PregnantHistoryFragmentInterface.View>
        implements PregnantHistoryFragmentInterface.Presenter {

    public static PregnantHistoryFragmentInterface.Presenter create() {
        return new PregnantHistoryFragmentPresenter();
    }

    @Override
    public void getPatientMenu(int patientMenuId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiEhrMenuObject> call = getRetrofitToken(access_token).getPatientMenu(patientMenuId);
        call.enqueue(new Callback<ApiEhrMenuObject>() {
            @Override
            public void onResponse(Call<ApiEhrMenuObject> call, Response<ApiEhrMenuObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    EhrMenuObject data = response.body().getMenu();
                    data.setMenuName(StringUtils.setNewLine(data.getMenuName()));
                    data.setDisplayName(StringUtils.setNewLine(data.getDisplayName()));
                    getView().setPatientMenu(data);

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
            public void onFailure(Call<ApiEhrMenuObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getPlaceSelectList(int patientId, int patientMenuId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListPlacePregnantHistoryCriteriaObject> call = getRetrofitToken(access_token).getPregnantPlaceListCriteria(patientId, patientMenuId);
        call.enqueue(new Callback<ApiListPlacePregnantHistoryCriteriaObject>() {
            @Override
            public void onResponse(Call<ApiListPlacePregnantHistoryCriteriaObject> call, Response<ApiListPlacePregnantHistoryCriteriaObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<String> criteriaList = response.body().getCriteriaList();
                    ArrayList<BottomSheetObject> dataList = new ArrayList<>();

                    for (String criteria : criteriaList) {

                        BottomSheetObject data = new BottomSheetObject();
                        data.setDescriptions(criteria);
                        data.setValueItem(criteria);
                        dataList.add(data);
                    }

                    getView().setPlaceSelectListCriteria(dataList);

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
            public void onFailure(Call<ApiListPlacePregnantHistoryCriteriaObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getDefaultDateCriteria(int patientId, int patientMenuId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiPregnantHistoryCriteriaObject> call = getRetrofitToken(access_token).getPregnantDefaultDateCriteria(patientId, patientMenuId);
        call.enqueue(new Callback<ApiPregnantHistoryCriteriaObject>() {
            @Override
            public void onResponse(Call<ApiPregnantHistoryCriteriaObject> call, Response<ApiPregnantHistoryCriteriaObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    PregnantHistoryCriteriaObject criteria = response.body().getCriteria();
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
            public void onFailure(Call<ApiPregnantHistoryCriteriaObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
