package com.truedigital.vhealth.ui.detaildoctor;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.database.UserManager;
import com.truedigital.vhealth.model.ApiDoctorObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class DetailDoctorActivityPresenter extends BaseMvpPresenter<DetailDoctorActivityInterface.View>
        implements DetailDoctorActivityInterface.Presenter {

    public static DetailDoctorActivityInterface.Presenter create() {
        return new DetailDoctorActivityPresenter();
    }

    @Override
    public void loadDoctorInfo(int doctorId) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }

        getView().showLoading();
        UserManager userManager = new UserManager();
        String access_token = userManager.getAccess_Token(userManager.getCurrentUserId());
        Call<ApiDoctorObject> call = getRetrofitToken(access_token).postDoctorInfo(doctorId);
        call.enqueue(new Callback<ApiDoctorObject>() {
            @Override
            public void onResponse(Call<ApiDoctorObject> call, Response<ApiDoctorObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        getView().showDoctorInfo(response.body().getAccountObject());
                    }
                    else {
                        getView().showMessage(response.body().getErrorMessage());
                    }
                }
                else {
                    getView().showMessage(response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<ApiDoctorObject> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage().toString());
            }
        });
    }
}