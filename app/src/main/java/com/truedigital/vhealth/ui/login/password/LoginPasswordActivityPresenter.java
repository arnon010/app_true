package com.truedigital.vhealth.ui.login.password;

import android.util.Log;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiAccessToken;
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

public class LoginPasswordActivityPresenter extends BaseMvpPresenter<LoginPasswordActivityInterface.View>
        implements LoginPasswordActivityInterface.Presenter{

    private static final String TAG = LoginPasswordActivityPresenter.class.getName();

    public static LoginPasswordActivityInterface.Presenter create() {
        return new LoginPasswordActivityPresenter();
    }

    @Override
    public void onLoginButtonClick() {
        if (getView().isValid()) {
            callLogin(getView().getPhoneNumber(),getView().getPassword());
        }
    }

    private void callLogin(String telephoneNumber, String password) {
        //if (!getView().isNetworkConnected()) {
        //    getView().showMessage(R.string.network_disconnect);
        //    return ;
        //}

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
                    Log.e(TAG,"Login "+response.body().getAccess_token());

                    AppManager.getDataManager().setAccessToken(data);
                    AppManager.getDataManager().setLogin();
                    //..
                    addOneSignalToken(response.body().getAccess_token());
                    //..
                    getView().openMainActivity();
                    //callUserInfo(data.getAccess_token(), data.getRefresh_token());
                }
                else {
                    Log.e(TAG,"Login fail");
                    getView().showErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApiAccessToken> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage());
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

                    AppManager.getDataManager().Login(access_token,refresh_token,data);

                    //..
                    addOneSignalToken(access_token);
                    //..
                    getView().openMainActivity();
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
        Log.e(TAG,"OneSignal player ID " +  udid);

        Call<NormalResponseObject> call = getRetrofitToken(access_token).postAddOneSignalToken(request);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    //..Success
                    Log.e(TAG,"Add token OneSignal Success");
                }
                else {
                    Log.e(TAG,"Add token OneSignal Fail");
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


