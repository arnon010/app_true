package com.truedigital.vhealth.ui.ehr.doctornote;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListBankAccountObject;
import com.truedigital.vhealth.model.ApiRecommendProductCouponsObject;
import com.truedigital.vhealth.model.BankAccountObject;
import com.truedigital.vhealth.model.DoctorNoteObject;
import com.truedigital.vhealth.model.RecommendMedicationObject;
import com.truedigital.vhealth.model.RecommendProductObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;


public class DoctorNoteDetailFragmentPreenter  extends BaseMvpPresenter<DoctorNoteDetailFragmentInterface.View>
        implements DoctorNoteDetailFragmentInterface.Presenter{

    public static DoctorNoteDetailFragmentInterface.Presenter create(){
        return new DoctorNoteDetailFragmentPreenter();
    }

    @Override
    public void getDetailCoupons(DoctorNoteObject data, String couponsCode, final boolean isProduct) {
        getView().showLoading();

        ArrayList<Integer> productIdList = new ArrayList<>();
        if(isProduct)
        {
            for (RecommendProductObject item : data.getRecommendProductList()) {
                productIdList.add(item.getProductId());
            }
        }
        else
        {
            for (RecommendMedicationObject item : data.getRecommendMedicineList()) {
                productIdList.add(item.getProductId());
            }
        }

        ArrayList<Integer> typeList = new ArrayList<>();
        if(isProduct)
        {
            for (RecommendProductObject item : data.getRecommendProductList()) {
                typeList.add(item.getPromotionType());
            }
        }
        else
        {
            for (RecommendMedicationObject item : data.getRecommendMedicineList()) {
                typeList.add(item.getPromotionType());
            }
        }

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiRecommendProductCouponsObject> call = getRetrofitToken(access_token).postCheckRecommendProductCoupons(couponsCode, data.getAppointmentId(), productIdList, typeList);
        call.enqueue(new Callback<ApiRecommendProductCouponsObject>() {
            @Override
            public void onResponse(Call<ApiRecommendProductCouponsObject> call, Response<ApiRecommendProductCouponsObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    float discountPrice = response.body().getDiscountPrice();
                    getView().setDetailCoupons(discountPrice, isProduct);

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
            public void onFailure(Call<ApiRecommendProductCouponsObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getBankAccountList() {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListBankAccountObject> call = getRetrofitToken(access_token).getBankAccountList();
        call.enqueue(new Callback<ApiListBankAccountObject>() {
            @Override
            public void onResponse(Call<ApiListBankAccountObject> call, Response<ApiListBankAccountObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<BankAccountObject> dataList = response.body().getDataList();

                    getView().setBankAccountList(dataList);

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
            public void onFailure(Call<ApiListBankAccountObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
