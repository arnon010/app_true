package com.truedigital.vhealth.ui.ehr.doctornote;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiDoctorNoteObject;
import com.truedigital.vhealth.model.ApiListDoctorNoteObject;
import com.truedigital.vhealth.model.DoctorNoteObject;
import com.truedigital.vhealth.model.DoctorNoteCriteriaObject;
import com.truedigital.vhealth.model.RecommendMedicationObject;
import com.truedigital.vhealth.model.RecommendProductObject;
import com.truedigital.vhealth.model.ShortNoteAttachmentObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListDoctorNoteFragmentPresenter extends BaseMvpPresenter<ListDoctorNoteFragmentInterface.View>
        implements ListDoctorNoteFragmentInterface.Presenter {

    public static ListDoctorNoteFragmentInterface.Presenter create() {
        return new ListDoctorNoteFragmentPresenter();
    }

    public void getDoctorNoteList(DoctorNoteCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListDoctorNoteObject> call = getRetrofitToken(access_token).getDoctorNoteList(criteria.getPatientId(), criteria.getDoctorId(), criteria.getStartDateString(), criteria.getEndDateString(), criteria.getPageIndex(), criteria.getPageSize());
        call.enqueue(new Callback<ApiListDoctorNoteObject>() {
            @Override
            public void onResponse(Call<ApiListDoctorNoteObject> call, Response<ApiListDoctorNoteObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<DoctorNoteObject> dataList = response.body().getDataList();
                    for (DoctorNoteObject data : dataList) {

                        if (data.getAppointmentTimeString() != null && data.getAppointmentTimeString() != "") {

                            data.setAppointmentTime(ConvertDate.StrToDateFormat(data.getAppointmentTimeString()));
                        }
                    }

                    getView().setDoctorNoteList(dataList);

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
            public void onFailure(Call<ApiListDoctorNoteObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getDoctorNoteDetail(final DoctorNoteObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiDoctorNoteObject> call = getRetrofitToken(access_token).getDoctorNoteDetail(criteria.getPatientId(), criteria.getAppointmentNumber());
        call.enqueue(new Callback<ApiDoctorNoteObject>() {
            @Override
            public void onResponse(Call<ApiDoctorNoteObject> call, Response<ApiDoctorNoteObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    DoctorNoteObject data = response.body().getData();
                    data.setContactMinutes(criteria.getContactMinutes());

                    for (ShortNoteAttachmentObject attachment : data.getShortNoteAttachmentList()) {
                        attachment.setIsVideo(false);
                    }

                    if (data.getAppointmentTimeString() != null && data.getAppointmentTimeString() != "") {

                        data.setAppointmentTime(ConvertDate.StrToDateFormat(data.getAppointmentTimeString()));
                    }

                    if (data.getEndOfEffectiveDateString() != null && data.getEndOfEffectiveDateString() != "") {

                        data.setEndOfEffectiveDate(ConvertDate.StrToDateFormat(data.getEndOfEffectiveDateString()));
                    }

                    if (data.getRecommendProductList() != null && data.getRecommendProductList().size() > 0) {

                        for (RecommendProductObject product : data.getRecommendProductList()) {

                            if (product.getEndOfEffectiveDateString() != null && product.getEndOfEffectiveDateString() != "") {

                                product.setEndOfEffectiveDate(ConvertDate.StrToDateFormat(product.getEndOfEffectiveDateString()));
                            }
                        }
                    }

                    if (data.getRecommendMedicineList() != null && data.getRecommendMedicineList().size() > 0) {

                        for (RecommendMedicationObject medicine : data.getRecommendMedicineList()) {

                            if (medicine.getEndOfEffectiveDateString() != null && medicine.getEndOfEffectiveDateString() != "") {

                                medicine.setEndOfEffectiveDate(ConvertDate.StrToDateFormat(medicine.getEndOfEffectiveDateString()));
                            }
                        }
                    }

                    getView().setDoctorNoteDetail(data);

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
            public void onFailure(Call<ApiDoctorNoteObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }


}
