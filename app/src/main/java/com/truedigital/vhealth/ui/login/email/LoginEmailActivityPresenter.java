package com.truedigital.vhealth.ui.login.email;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginEmailActivityPresenter extends BaseMvpPresenter<LoginEmailActivityInterface.View>
        implements LoginEmailActivityInterface.Presenter {

    private static final String TAG = LoginEmailActivityPresenter.class.getName();

    public static LoginEmailActivityInterface.Presenter create() {
        return new LoginEmailActivityPresenter();
    }


    @Override
    public void onLoginButtonClick() {
        if (getView().isValid()) {
            //callApiCheckPhone(getView().getPhoneNumber());
        }
    }
/*
    private void callApiCheckPhone(final String telephoneNumber) {
        getView().showLoading();

        Call<ApiCheckTelephone> call = getRetrofitToken("").postCheckTelephone(telephoneNumber);
        call.enqueue(new Callback<ApiCheckTelephone>() {
            @Override
            public void onResponse(Call<ApiCheckTelephone> call, Response<ApiCheckTelephone> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ApiCheckTelephone apiCheckTelephone = response.body();
                    if (apiCheckTelephone.isHasAccount()) {
                        //getView().showMessage("Has Account");
                        //callLogin(telephoneNumber);
                        getView().openLoginWithPassword();
                    } else {
                        getView().openLoginWithOtp("");
                        //getView().showMessage("No Account");
                        //callApiGetOTP(getView().getPhoneNumber(),true);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiCheckTelephone> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }


    @Override
    public void onRenewOtpButtonClick() {
        //callApiGetOTP(getView().getPhoneNumber(), false);
    }

    private void callApiGetOTP(final String telephoneNumber, final boolean isShowDialog) {
        getView().showLoading();

        Call<ApiGenerateOTP> call = getRetrofitServiceAuthen().postGetOTP(telephoneNumber);
        call.enqueue(new Callback<ApiGenerateOTP>() {
            @Override
            public void onResponse(Call<ApiGenerateOTP> call, Response<ApiGenerateOTP> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ApiGenerateOTP data = response.body();
                    Log.e(TAG, data.getReference());
                    if (isShowDialog) {
                        getView().openLoginWithOtp(data.getReference());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiGenerateOTP> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    private void callLogin(String telephoneNumber) {
        getView().showLoading();
        Call<ApiAccessToken> call = getRetrofitServiceAuthen().postLogin(
                AppConstants.AUTHEN_CLIENT_ID,
                AppConstants.AUTHEN_CLIENT_SECRET,
                AppConstants.AUTHEN_TYPE,
                AppConstants.AUTHEN_GRANT_TYPE,
                telephoneNumber,
                "");

        call.enqueue(new Callback<ApiAccessToken>() {
            @Override
            public void onResponse(Call<ApiAccessToken> call, Response<ApiAccessToken> response) {
                if (response.isSuccessful()) {
                    ApiAccessToken data = response.body();
                    if (data.getAccess_token() != null) {
                        Log.e(TAG, "" + response.body().getAccess_token());

                        //callUserInfo(data.getAccess_token(), data.getRefresh_token());

                    } else {
                        Log.e(TAG, "error" + response.body().getAccess_token());
                        getView().showMessage(data.getErrorMessage());
                    }
                } else {
                    Log.e(TAG, "" + response.body().getAccess_token());
                    getView().showMessage(response.message());
                }
                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiAccessToken> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
    */
}


