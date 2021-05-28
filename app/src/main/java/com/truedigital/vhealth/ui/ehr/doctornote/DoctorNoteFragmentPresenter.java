package com.truedigital.vhealth.ui.ehr.doctornote;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiDoctorNoteCriteriaObject;
import com.truedigital.vhealth.model.ApiListDoctorNoteCriteriaObject;
import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.DoctorNoteCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class DoctorNoteFragmentPresenter extends BaseMvpPresenter<DoctorNoteFragmentInterface.View>
        implements DoctorNoteFragmentInterface.Presenter {

    public static DoctorNoteFragmentInterface.Presenter create() {
        return new DoctorNoteFragmentPresenter();
    }

    @Override
    public void getDoctorSelectList(int patientId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListDoctorNoteCriteriaObject> call = getRetrofitToken(access_token).getDoctorNoteDoctorListCriteria(patientId);
        call.enqueue(new Callback<ApiListDoctorNoteCriteriaObject>() {
            @Override
            public void onResponse(Call<ApiListDoctorNoteCriteriaObject> call, Response<ApiListDoctorNoteCriteriaObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<DoctorNoteCriteriaObject> criteriaList = response.body().getCriteriaList();
                    ArrayList<BottomSheetObject> dataList = new ArrayList<>();

                    for (DoctorNoteCriteriaObject criteria : criteriaList) {

                        BottomSheetObject data = new BottomSheetObject();
                        data.setDescriptions(criteria.getDoctorName());
                        data.setValueItem(Integer.toString(criteria.getDoctorId()));
                        dataList.add(data);
                    }

                    getView().setDoctorSelectListCriteria(dataList);

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
            public void onFailure(Call<ApiListDoctorNoteCriteriaObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getDefaultDateCriteria(int patientId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiDoctorNoteCriteriaObject> call = getRetrofitToken(access_token).getDoctorNoteDefaultDateCriteria(patientId);
        call.enqueue(new Callback<ApiDoctorNoteCriteriaObject>() {
            @Override
            public void onResponse(Call<ApiDoctorNoteCriteriaObject> call, Response<ApiDoctorNoteCriteriaObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    DoctorNoteCriteriaObject criteria = response.body().getCriteria();
                    if(criteria.getStartDateString() != null && criteria.getStartDateString() != "")
                    {
                        criteria.setStartDate(ConvertDate.StrToDateFormat(criteria.getStartDateString()));
                    }

                    if(criteria.getEndDateString() != null && criteria.getEndDateString() != "")
                    {
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
            public void onFailure(Call<ApiDoctorNoteCriteriaObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
