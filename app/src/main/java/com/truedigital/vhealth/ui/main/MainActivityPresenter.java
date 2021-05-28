package com.truedigital.vhealth.ui.main;

import android.util.Log;

import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiCheckPinObject;
import com.truedigital.vhealth.model.ApiCurrentVersion;
import com.truedigital.vhealth.model.SystemConfigurationObject;
import com.truedigital.vhealth.model.appointment.ApiItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getAppointmentService;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class MainActivityPresenter extends BaseMvpPresenter<MainActivityInterface.View>
        implements MainActivityInterface.Presenter {

    private static final String TAG = "Main";

    public static MainActivityInterface.Presenter create() {
        return new MainActivityPresenter();
    }

    @Override
    public void getSystemConfigurationApi() {

        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }
        getView().showLoading();

        Call<SystemConfigurationObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getSystemConfigurationApi("Android");
        call.enqueue(new Callback<SystemConfigurationObject>() {
            @Override
            public void onResponse(Call<SystemConfigurationObject> call, Response<SystemConfigurationObject> response) {
                if (response.isSuccessful()) {

                    SystemConfigurationObject data = response.body();

                    getView().setSystemConfigurationApi(data);
                    Log.e("","get config Success");

                } else {

                    getView().showMessage(response);
                    Log.e("","get config fail");
                    /*
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                    */
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<SystemConfigurationObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getPINStatus() {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }

        getView().showLoading();

        final String access_token = AppManager.getDataManager().getAccess_token();
        Call<ApiCheckPinObject> call = getRetrofitToken(access_token).getPINStatus();
        call.enqueue(new Callback<ApiCheckPinObject>() {
            @Override
            public void onResponse(Call<ApiCheckPinObject> call, Response<ApiCheckPinObject> response) {
                if (response.isSuccessful()) {
                    getView().onNavigateEHR(response.body().getHasPIN());
                }
                else {
                    /*
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                    */
                    getView().showMessage(response);
                }
                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiCheckPinObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    /*
    @Override
    public void onLogout() {
        super.onLogout();
        getView().openLogin();
    }
    */

    /*
    @Override
    public void onRefresh_token(String refresh_token) {
        getView().showLoading();
        Call<ApiAccessToken> call = getRetrofitServiceAuthen().postRefreshToken(
                AppConstants.AUTHEN_CLIENT_ID,
                AppConstants.AUTHEN_CLIENT_SECRET,
                AppConstants.AUTHEN_TYPE,
                AppConstants.AUTHEN_GRANT_TYPE_REFRESH,
                refresh_token
        );
        call.enqueue(new Callback<ApiAccessToken>() {
            @Override
            public void onResponse(Call<ApiAccessToken> call, Response<ApiAccessToken> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ApiAccessToken data = response.body();
                    AppManager.getDataManager().Login(data);
                    //callUserInfo(data.getAccess_token(), data.getRefresh_token());
                }
                else {
                    getView().token_expired();
                }
            }

            @Override
            public void onFailure(Call<ApiAccessToken> call, Throwable t) {
                getView().hideLoading();
                getView().token_expired();
            }
        });
    }
    */

    @Override
    public void callServiceGetAppointment(String appointmentNumber) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }

        Call<ApiItemAppointmentDao> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getAppointmentInfo(appointmentNumber);
        call.enqueue(new Callback<ApiItemAppointmentDao>() {
            @Override
            public void onResponse(Call<ApiItemAppointmentDao> call, Response<ApiItemAppointmentDao> response) {
                if (response.isSuccessful()) {
                    getView().canStartAppointment(response.body().getData().isCanStart());
                }
                else {
                    getView().canStartAppointment(false);
                }
            }

            @Override
            public void onFailure(Call<ApiItemAppointmentDao> call, Throwable t) {
                //getView().canStartAppointment(false);
            }
        });
    }

    @Override
    public void getApplicationVersion() {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }

        Call<ApiCurrentVersion> call = getRetrofitToken("").CheckVersion(AppConstants.APP_MOBILE_OS,AppConstants.AUTHEN_CLIENT_ID);
        call.enqueue(new Callback<ApiCurrentVersion>() {
            @Override
            public void onResponse(Call<ApiCurrentVersion> call, Response<ApiCurrentVersion> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG,"Current " + BuildConfig.VERSION_CODE + " Last " + response.body().getLast());
                    if (response.body().getLast() > BuildConfig.VERSION_CODE) {
                        //getView().foundNewVersion();
                    }
                }
                else {
                    //Log.e(TAG,"" + response.raw().message());
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiCurrentVersion> call, Throwable t) {
                Log.e(TAG,""+t.getMessage());
            }
        });
    }
}