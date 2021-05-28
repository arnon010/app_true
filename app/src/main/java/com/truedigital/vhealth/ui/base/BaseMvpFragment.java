package com.truedigital.vhealth.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.snackbar.Snackbar;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.ui.base.exception.MvpNotSetLayoutException;
import com.truedigital.vhealth.ui.base.exception.MvpPresenterNotCreateException;
import com.truedigital.vhealth.ui.login.LoginActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;


/**
 * Created by songkrit on 12/20/2017 AD.
 */

public abstract class BaseMvpFragment<P extends BaseMvpInterface.Presenter>
        extends Fragment
        implements BaseMvpInterface.View {

    protected ViewBinding viewBinding;

    private P presenter;
    private Dialog mProgressDialog;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = createPresenter();
        presenter.attachView(this);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (viewBinding != null) {
            return viewBinding.getRoot();
        }

        int layoutResId = getLayoutView();
        if (getLayoutView() == 0) throw new MvpNotSetLayoutException();
        return inflater.inflate(layoutResId, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bindView(view);
        getPresenter().onViewCreate();
        if (savedInstanceState == null) {
            initialize();
        } else {
            restoreView(savedInstanceState);
        }
        setupInstance();
        setupView();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onViewStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onViewStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onViewDestroy();
        presenter.detachView();
    }

    @Override
    public P getPresenter() {
        if (presenter != null) return presenter;
        throw new MvpPresenterNotCreateException();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null || !mProgressDialog.isShowing()) {
            hideLoading();
            mProgressDialog = CommonUtils.showLoadingDialog(getActivity());
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(
                getActivity().findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG
        );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_green));
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(
                R.string.error_message_button_ok,
                (dialogInterface, i) -> {
                }
        );

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
        if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
            return;
        }

        if (response != null && response.errorBody() != null) {
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
                this.showMessage("" + e.getMessage());
            }
        }
    }

    @Override
    public void showErrorMessage(ResponseBody errorBody) {
        if (errorBody != null) {
            try {
                JSONObject objError = new JSONObject(errorBody.string());
                showMessage(objError.getString("error_description"));
            } catch (Exception e) {
                showMessage("" + e.getMessage());
            }
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return CommonUtils.isNetworkConnected(getActivity());
    }

    @Override
    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public String getDeviceId() {
        return CommonUtils.getDeviceId(getActivity());
    }

    public void token_expired() {
        getPresenter().onRefresh_token(AppManager.getDataManager().getRefresh_token());
    }

    @Override
    public void token_refresh_error() {

        getPresenter().onLogout();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public String getTokenIdOneSignal() {
        return getTokenIdOneSignal();
    }

    public abstract P createPresenter();

    public abstract int getLayoutView();

    public abstract void bindView(View view);

    public abstract void setupInstance();

    public abstract void setupView();

    public abstract void initialize();

    public void restoreView(Bundle savedInstanceState) {
    }
}
