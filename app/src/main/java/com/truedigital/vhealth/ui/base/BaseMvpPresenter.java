package com.truedigital.vhealth.ui.base;

import android.util.Log;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.manager.DataManager;
import com.truedigital.vhealth.model.ApiAccessToken;
import com.truedigital.vhealth.model.ApiUserInfo;
import com.truedigital.vhealth.ui.base.exception.MvpViewNotAttachedException;
import com.truedigital.vhealth.utils.AppConstants;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitServiceAuthen;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public abstract class BaseMvpPresenter<V extends BaseMvpInterface.View>
        implements BaseMvpInterface.Presenter<V>{

    // View reference. We use as a WeakReference
    // because the Activity could be destroyed at any time
    // and we don't want to create a memory leak
    private WeakReference<V> mMvpView;

    @Override
    public void attachView( V mvpView ){
        mMvpView = new WeakReference<>( mvpView );
    }


    @Override
    public void detachView(){
        mMvpView = null;
    }

    @Override
    public V getView() throws NullPointerException{
        if( mMvpView != null ) return mMvpView.get();
        throw new MvpViewNotAttachedException();
    }

    @Override
    public void onViewCreate(){
    }


    @Override
    public void onViewStart(){
    }

    @Override
    public void onViewStop(){
    }

    @Override
    public void onViewDestroy(){
    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewPause() {

    }

    @Override
    public void onLogout() {
        //UserManager userManager = new UserManager();
        //userManager.logout(userManager.getCurrentUserId());
        //userManager.deleteAll();

        AppManager.getDataManager().Logout();
    }

    @Override
    public void onRefresh_token(String refresh_token) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }

        AppManager.getDataManager().setCanRefreshToken(false);
        getView().showLoading();
        Call<ApiAccessToken> call = getRetrofitServiceAuthen().postRefreshToken(
                AppConstants.AUTHEN_CLIENT_ID,
                AppConstants.AUTHEN_CLIENT_SECRET,
                //AppConstants.AUTHEN_TYPE,
                AppConstants.AUTHEN_GRANT_TYPE_REFRESH,
                refresh_token
        );
        call.enqueue(new Callback<ApiAccessToken>() {
            @Override
            public void onResponse(Call<ApiAccessToken> call, Response<ApiAccessToken> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ApiAccessToken data = response.body();
                    AppManager.getDataManager().setAccess_token(data.getAccess_token());
                    AppManager.getDataManager().setRefresh_token(data.getRefresh_token());
                    AppManager.getDataManager().setCanRefreshToken(true);
                }
                else {
                    //getView().token_expired();
                    getView().token_refresh_error();
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
    public void onRefresh_token(String refresh_token, final OnRefreshListener listener) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }

        //AppManager.getDataManager().setCanRefreshToken(false);
        getView().showLoading();
        Call<ApiAccessToken> call = getRetrofitServiceAuthen().postRefreshToken(
                AppConstants.AUTHEN_CLIENT_ID,
                AppConstants.AUTHEN_CLIENT_SECRET,
                //AppConstants.AUTHEN_TYPE,
                AppConstants.AUTHEN_GRANT_TYPE_REFRESH,
                refresh_token
        );
        call.enqueue(new Callback<ApiAccessToken>() {
            @Override
            public void onResponse(Call<ApiAccessToken> call, Response<ApiAccessToken> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ApiAccessToken data = response.body();
                    AppManager.getDataManager().setAccess_token(data.getAccess_token());
                    AppManager.getDataManager().setRefresh_token(data.getRefresh_token());
                    AppManager.getDataManager().setCanRefreshToken(true);

                    Log.e("new Token ",AppManager.getDataManager().getAccess_token());
                    if (listener != null) {
                        listener.onSuccess();
                    }
                }
                else {
                    if (listener != null) {
                        listener.onFail();
                    }
                    getView().token_refresh_error();
                }
            }

            @Override
            public void onFailure(Call<ApiAccessToken> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage());
                if (listener != null) {
                    listener.onFail();
                }
            }
        });
    }

    @Override
    public void onCheckToken(String token, final OnTokenListener listener) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }

        getView().showLoading();
        Call<ApiUserInfo> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getCurrentUser();
        call.enqueue(new Callback<ApiUserInfo>() {
            @Override
            public void onResponse(Call<ApiUserInfo> call, Response<ApiUserInfo> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ApiUserInfo info = response.body();
                    if (info != null) {
                        DataManager dataManager = AppManager.getDataManager();
                        dataManager.setUserName(info.getUserName());
                        dataManager.setUserEmail(info.getEmail());
                        dataManager.setPhone(info.getPhone());
                    }

                    //..
                    Log.e("Token ","is not die");
                    if (listener != null) {
                        listener.onSuccess();
                    }
                }
                else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        Log.e("Token ","is die");
                        onRefresh_token(AppManager.getDataManager().getRefresh_token(), new OnRefreshListener() {
                            @Override
                            public void onSuccess() {
                                if (listener != null) {
                                    listener.onSuccess();
                                }
                            }

                            @Override
                            public void onFail() {
                                if (listener != null) {
                                    listener.onFail();
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiUserInfo> call, Throwable t) {
                //..onRefresh_token( AppManager.getDataManager().getRefresh_token());
                //..nothing
                if (listener != null) {
                    listener.onFail();
                }
            }
        });
    }

    public interface OnTokenListener {
        void onSuccess();
        void onFail();
    }

    public interface OnRefreshListener {
        void onSuccess();
        void onFail();
    }
}
