package com.truedigital.vhealth.ui.login;

import android.util.Log;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiAccessToken;
import com.truedigital.vhealth.model.ApiGenerateOTP;
import com.truedigital.vhealth.model.ApiOneSignalRequest;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitService;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitServiceAuthen;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginActivityPresenter extends BaseMvpPresenter<LoginActivityInterface.View>
        implements LoginActivityInterface.Presenter{

    private static final String TAG = "LoginActivityPresenter";

    public static LoginActivityInterface.Presenter create() {
        return new LoginActivityPresenter();
    }

    @Override
    public void onRequestOTPClick() {

    }

    @Override
    public void onEmailLoginClick() {
        getView().openLoginWithEmail();
    }

    @Override
    public void onFacebookLoginClick() {
        getView().openLoginFacebook();
    }

    private void callRequestOTP(String telephoneNumber) {
            getView().showLoading();
            Call<ApiGenerateOTP> call = getRetrofitService().postGenerateOTP(telephoneNumber);
            call.enqueue(new Callback<ApiGenerateOTP>() {
                @Override
                public void onResponse(Call<ApiGenerateOTP> call, Response<ApiGenerateOTP> response) {
                    getView().hideLoading();
                    if (response.isSuccessful()) {
                        Log.e(TAG,""+response.body().getReference());
                        getView().openLoginOTP(response.body().getReference());
                    }
                    else {
                        getView().showMessage(response.raw().message());
                    }
                }

                @Override
                public void onFailure(Call<ApiGenerateOTP> call, Throwable t) {
                    getView().hideLoading();
                    getView().showMessage(""+t.getMessage().toString());
                }
            });
    }

    private void callLogin(String telephoneNumber, String password) {
        getView().showLoading();
        Call<ApiAccessToken> call = getRetrofitServiceAuthen().postLogin(
                AppConstants.AUTHEN_CLIENT_ID,
                AppConstants.AUTHEN_CLIENT_SECRET,
                AppConstants.AUTHEN_METHOD_PASSWORD,
                AppConstants.AUTHEN_GRANT_TYPE,
                telephoneNumber,
                password);

        call.enqueue(new Callback<ApiAccessToken>() {
            @Override
            public void onResponse(Call<ApiAccessToken> call, Response<ApiAccessToken> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ApiAccessToken data = response.body();
                    Log.e(TAG,""+response.body().getAccess_token());
                    //callUserInfo(data.getAccess_token(), data.getRefresh_token());

                    AppManager.getDataManager().setLastTimeRefreshToken();
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAccessToken> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage());
            }
        });
    }

    @Override
    public void callLoginFacebook(String facebook_id, String facebook_token) {
        getView().showLoading();
        String userName = "" + facebook_id;
        String password="" + facebook_id;
        Call<ApiAccessToken> call = getRetrofitServiceAuthen().postLoginFacebook(
                AppConstants.AUTHEN_CLIENT_ID,
                AppConstants.AUTHEN_CLIENT_SECRET,
                AppConstants.AUTHEN_METHOD_FACEBOOK,
                AppConstants.AUTHEN_GRANT_TYPE,
                userName,
                password,
                facebook_id,
                facebook_token);

        call.enqueue(new Callback<ApiAccessToken>() {
            @Override
            public void onResponse(Call<ApiAccessToken> call, Response<ApiAccessToken> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ApiAccessToken data = response.body();
                    if (data.getAccess_token() != null) {
                        Log.e(TAG,""+response.body().getAccess_token());

                        AppManager.getDataManager().setAccessToken(data);
                        AppManager.getDataManager().setLogin();
                        AppManager.getDataManager().setLastTimeRefreshToken();
                        //..
                        addOneSignalToken(response.body().getAccess_token());
                        //..
                        getView().openMainActivity();
                    }
                    else {
                        getView().showMessage(response.body().getErrorMessage());
                    }

                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAccessToken> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage().toString());
            }
        });

    }


    private String getOneSignalPlayerId() {
        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();

        return status.getSubscriptionStatus().getUserId();
    }

    private void addOneSignalToken(final String access_token) {
        String udid = getOneSignalPlayerId();
        String tokenOneSignal = getView().getTokenIdOneSignal();

        ApiOneSignalRequest request = new ApiOneSignalRequest();
        request.setUdId(udid);
        request.setToken(tokenOneSignal);
        request.setUserId(AppManager.getDataManager().getUserId());
        request.setOS(AppConstants.APP_MOBILE_OS);

        Call<NormalResponseObject> call = getRetrofitToken(access_token).postAddOneSignalToken(request);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    //..Success
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().showMessage(""+t.getMessage());
            }
        });
    }

}
