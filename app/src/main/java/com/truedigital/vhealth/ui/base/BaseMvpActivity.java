package com.truedigital.vhealth.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.StringRes;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.viewbinding.ViewBinding;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.custom.NetworkStatusReceiver;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.ui.base.exception.MvpNotSetLayoutException;
import com.truedigital.vhealth.ui.base.exception.MvpPresenterNotCreateException;
import com.truedigital.vhealth.ui.login.LoginActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.StringUtils;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public abstract class BaseMvpActivity<P extends BaseMvpInterface.Presenter>
        extends LocalizationActivity
        implements BaseMvpInterface.View {

    protected ViewBinding viewBinding;

    private static final String TAG = "BaseActivity";
    private P presenter;
    private Dialog mProgressDialog;
    private String tokenOneSignal;
    //private BroadcastReceiver mNetworkStatusReceiver;
    private NetworkStatusReceiver mNetworkStatusReceiver;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        presenter = createPresenter();
        presenter.attachView( this );

        if (viewBinding != null) {
            setContentView(viewBinding.getRoot());
        } else {
            int layoutResId = getLayoutView();
            if (layoutResId == 0) throw new MvpNotSetLayoutException();
            setContentView(layoutResId);
        }

        AppManager.setActivityVisible(true);
        mNetworkStatusReceiver = new NetworkStatusReceiver(this);
        mNetworkStatusReceiver.setCallback(new NetworkStatusReceiver.Callbacks() {
            @Override
            public void onNetworkUpdated(boolean isConnected) {
                if (AppManager.isActivityVisible()) {
                    if (!isConnected) {
                        showMessage(R.string.network_disconnect);
                        Log.e(TAG,"Internet Lost");
                    }
                    else {
                        Log.e(TAG,"Internet connected");
                    }
                }
            }
        });
        //registerNetwork();

        bindView();
        initInstance();
        setupView();
        getPresenter().onViewCreate();
        if( savedInstanceState == null ){
            initialize();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        AppManager.setActivityVisible(true);
        //registerNetwork();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppManager.setActivityVisible(false);
        //unregisterNetwork();
    }

    @Override
    protected void onStart(){
        super.onStart();
        getPresenter().onViewStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        getPresenter().onViewStop();
    }


    private void registerNetwork() {

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mNetworkStatusReceiver, filter);

    }

    private void unregisterNetwork() {

        if (mNetworkStatusReceiver != null) {
            unregisterReceiver(mNetworkStatusReceiver);
        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        getPresenter().onViewDestroy();
        presenter.detachView();
    }


    @Override
    public P getPresenter(){
        if( presenter != null ) return presenter;
        throw new MvpPresenterNotCreateException();
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
    }


    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );
        restoreView(savedInstanceState);
    }

    public void restoreView( Bundle savedInstanceState ){}

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.error_message));
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (this.isFinishing()) return;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(R.string.error_message_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showMessage(Response response) {
        //.. 401
        if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
            return ;
        }

        if(response != null && response.errorBody() != null)
        {
            try {

                JSONObject objError = new JSONObject(response.errorBody().string());
                JSONArray jArr = objError.getJSONArray("ErrorMessages");
                if (jArr != null) {
                    ArrayList<String> messages = new ArrayList<>();
                    for (int i = 0; i < jArr.length(); i++) {
                        messages.add(jArr.get(i).toString());
                    }

                    this.showMessage(StringUtils.getMessageNewline(messages));
                }

            } catch (Exception e) {
                this.showMessage("" + toMessage( e.getMessage() ));
            }
        }
    }

    @Override
    public void showErrorMessage(ResponseBody errorBody) {
        if(errorBody != null) {
            try {
                JSONObject objError = new JSONObject(errorBody.string());
                String errMessage = objError.getString("error_description");
                showMessage(toMessage(errMessage));

            } catch (Exception e) {
                showMessage("" + toMessage(e.getMessage()));
            }
        }
    }

    private String toMessage(String message) {
        String newMessage = message;
        String wordDoctype = "<!DOCTYPE";
        String wordNoVaule = "No value for ErrorMessages";

        if (message.toLowerCase().indexOf(wordDoctype.toLowerCase()) > -1) {
            newMessage = "Please try again.";
        }
        if (message.toLowerCase().equalsIgnoreCase(wordNoVaule.toLowerCase())) {
            newMessage = "Please try again.";
        }

        return newMessage;
    }


    /*
    private void openLogin() {
        //..logout
        getPresenter().onLogout();

        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        finish();
    }
    */

    @Override
    public boolean isNetworkConnected() {
        return CommonUtils.isNetworkConnected(this);
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public String getDeviceId() {
        return CommonUtils.getDeviceId(this);
    }

    @Override
    public void token_expired() {
        getPresenter().onRefresh_token(AppManager.getDataManager().getRefresh_token());

        /*
        //showMessage(R.string.error_message_token_expired);
        getPresenter().onLogout();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        */
    }

    @Override
    public void token_refresh_error() {
        getPresenter().onLogout();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

    @Override
    public String getTokenIdOneSignal() {
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                tokenOneSignal = registrationId;
            }
        });
        return tokenOneSignal;
    }



    protected abstract P createPresenter();

    protected abstract int getLayoutView();

    protected abstract void bindView();

    protected abstract void initInstance();

    protected abstract void setupView();

    protected abstract void initialize();

}
