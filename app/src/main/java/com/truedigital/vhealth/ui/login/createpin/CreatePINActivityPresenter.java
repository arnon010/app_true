package com.truedigital.vhealth.ui.login.createpin;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiPatientObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class CreatePINActivityPresenter extends BaseMvpPresenter<CreatePINActivityInterface.View>
        implements CreatePINActivityInterface.Presenter {

    public static CreatePINActivityInterface.Presenter create() {
        return new CreatePINActivityPresenter();
    }

    @Override
    public void onLogin(String pin, String confirmPin) {
        getView().showLoading();

        final String access_token = AppManager.getDataManager().getAccess_token();
        final String newPin = pin;

        Call<NormalResponseObject> callSetPin = getRetrofitToken(access_token).postCreatePin(pin, confirmPin);
        callSetPin.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    Call<ApiPatientObject> callLogin = getRetrofitToken(access_token).postLoginEHR(newPin);
                    callLogin.enqueue(new Callback<ApiPatientObject>() {
                        @Override
                        public void onResponse(Call<ApiPatientObject> call, Response<ApiPatientObject> response) {
                            if (response.isSuccessful() && response.body().isSuccess()) {

                                AppManager.getDataManager().setPatientId(response.body().getData().getPatientId());

                                getView().onLoginSuccess();

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
                        public void onFailure(Call<ApiPatientObject> call, Throwable t) {
                            getView().hideLoading();
                        }
                    });

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
