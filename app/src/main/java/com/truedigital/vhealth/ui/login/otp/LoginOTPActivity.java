package com.truedigital.vhealth.ui.login.otp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.setting.SettingActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.StringUtils;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginOTPActivity extends BaseMvpActivity<LoginOTPActivityInterface.Presenter>
        implements LoginOTPActivityInterface.View{

    private static final String KEY_RFERENCE_CODE = "KEY_RFERENCE_CODE";
    private Context context;
    private Button btn_confirm;
    private Button btn_renew;
    private TextView tv_title;
    private EditText ed_login_otp;
    private String mReferenceOTP;

    @Override
    protected LoginOTPActivityInterface.Presenter createPresenter() {
        return LoginOTPActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.dialog_login_otp;
    }

    @Override
    protected void bindView() {

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_renew = (Button) findViewById(R.id.btn_renew_otp);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ed_login_otp = (EditText) findViewById(R.id.ed_login_otp);

        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(btn_confirm);
        StringUtils.setUnderline(btn_renew);
    }

    @Override
    protected void initInstance()
    {
        //..fix bug translucent on android 8
        if (Build.VERSION.SDK_INT == 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void setupView()
    {
        setupToolbar();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onConfirmOtpClick();
            }
        });

        btn_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onResendOtpClick();
            }
        });

    }


    private void setupToolbar() {

    }

    @Override
    protected void initialize() {
        Log.e("OTP","savedInstanceState is null");
        getPresenter().callApiGetOTP(getPhoneNumber());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_RFERENCE_CODE,mReferenceOTP);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mReferenceOTP = savedInstanceState.getString(KEY_RFERENCE_CODE);
    }

    @Override
    public String getPhoneNumber() {
        return getIntent().getStringExtra(AppConstants.EXTRA_MOBILE_NO);
    }

    @Override
    public String getOTP() {
        return ed_login_otp.getText().toString();
    }

    @Override
    public String getReferenceOTP() {
        return mReferenceOTP;
    }

    @Override
    public void setReferenceOTP(String referenceOTP) {
        mReferenceOTP = referenceOTP;
    }

    @Override
    public void onErrorOTP() {
        ed_login_otp.setError(getString(R.string.error_invalid_otp));
        ed_login_otp.requestFocus();
    }

    @Override
    public boolean isValid() {
        if (ed_login_otp.getText().length() < AppConstants.VALID_OTP_LENGTH) {
            onErrorOTP();
            return false;
        }

        return true;
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent( this, MainActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        finish();
    }

    @Override
    public void openSignupSuccess() {
        openUserSetting();
        /*
        new MyDialog(this).showSignupSuccess(new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                openUserSetting();
            }

            @Override
            public void onClickCancel() {

            }
        });
        */
    }

    @Override
    public void openUserSetting() {
        Intent intent = new Intent( this, SettingActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(AppConstants.EXTRA_SIGNUP_MODE,true);
        intent.putExtra(AppConstants.EXTRA_MOBILE_NO, getPhoneNumber());
        intent.putExtra(AppConstants.EXTRA_SIGNUP_REFERENC_CODE,getReferenceOTP());
        intent.putExtra(AppConstants.EXTRA_SIGNUP_OTP,getOTP());
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }
}
