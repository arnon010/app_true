package com.truedigital.vhealth.ui.login.otp;

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

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitServiceAuthen;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginOTPActivityPresenter extends BaseMvpPresenter<LoginOTPActivityInterface.View>
        implements LoginOTPActivityInterface.Presenter{

    private static final String TAG = LoginOTPActivityPresenter.class.getName();

    public static LoginOTPActivityInterface.Presenter create() {
        return new LoginOTPActivityPresenter();
    }

    @Override
    public void onConfirmOtpClick() {
        if (getView().isValid()) {
            String phoneNumber = getView().getPhoneNumber();
            String reference_code = getView().getReferenceOTP();
            String otp = getView().getOTP();

            callApiLogin(phoneNumber,reference_code, otp);
        }
    }

    @Override
    public void onResendOtpClick() {
        callApiGetOTP(getView().getPhoneNumber());
    }

    @Override
    public void callApiGetOTP(String telephoneNumber) {
        getView().showLoading();

        Call<ApiGenerateOTP> call = getRetrofitServiceAuthen().postGetOTP(telephoneNumber);
        call.enqueue(new Callback<ApiGenerateOTP>() {
            @Override
            public void onResponse(Call<ApiGenerateOTP> call, Response<ApiGenerateOTP> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setReferenceOTP(response.body().getReference());
                    Log.e("OTP ","reference is "+response.body().getReference());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiGenerateOTP> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage());
            }
        });
    }

    private void callApiLogin(String telephoneNumber,String reference_code, String otp) {
        getView().showLoading();
        Call<ApiAccessToken> call = getRetrofitServiceAuthen().postLogin(
                AppConstants.AUTHEN_CLIENT_ID,
                AppConstants.AUTHEN_CLIENT_SECRET,
                AppConstants.AUTHEN_METHOD_OTP,
                AppConstants.AUTHEN_GRANT_TYPE,
                telephoneNumber,
                otp,
                reference_code);

        call.enqueue(new Callback<ApiAccessToken>() {
            @Override
            public void onResponse(Call<ApiAccessToken> call, Response<ApiAccessToken> response) {
                if (response.isSuccessful()) {
                    ApiAccessToken data = response.body();
                    if (data.getAccess_token() != null) {
                        Log.e(TAG,""+response.body().getAccess_token());

                        AppManager.getDataManager().setAccessToken(data);
                        AppManager.getDataManager().setLogin();
                        AppManager.getDataManager().setLoginBy(AppConstants.APP_LOGIN_PHONE);
                        AppManager.getDataManager().setStatus(AppConstants.APP_STATUS_SIGNUP);
                        //..
                        addOneSignalToken(response.body().getAccess_token());
                        //..
                        getView().openSignupSuccess();

                        //callUserInfo(data.getAccess_token(), data.getRefresh_token());
                    }
                    else {
                        getView().showMessage(data.getErrorMessage());
                    }
                }
                else {
                    getView().showErrorMessage(response.errorBody());
                }
                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiAccessToken> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    /*
    private void callUserInfo(final String access_token, final String refresh_token) {
        Call<ApiUserInfo> call = getRetrofitToken(access_token).getCurrentUser();
        call.enqueue(new Callback<ApiUserInfo>() {
            @Override
            public void onResponse(Call<ApiUserInfo> call, Response<ApiUserInfo> response) {
                if (response.isSuccessful()) {
                    ApiUserInfo data = response.body();
                    Log.e(TAG,"access_token: " + access_token);
                    AppManager.getDataManager().Login(access_token,refresh_token,data);
                    getView().openSignupSuccess();
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiUserInfo> call, Throwable t) {
                getView().showMessage(""+t.getMessage().toString());
            }
        });
    }
    */

    private String getOneSignalPlayerId() {
        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();

        //status.getPermissionStatus().getEnabled();
        //status.getSubscriptionStatus().getSubscribed();
        //status.getSubscriptionStatus().getUserSubscriptionSetting();
        //status.getSubscriptionStatus().getUserId();
        //status.getSubscriptionStatus().getPushToken();

        return status.getSubscriptionStatus().getUserId();
    }

    private void addOneSignalToken(final String access_token) {
        String udid = getOneSignalPlayerId();
        String tokenOneSignal = getView().getTokenIdOneSignal();
        //callServiceAddToken(data.getAccess_token(),udid,tokenOneSignal,"0");

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


