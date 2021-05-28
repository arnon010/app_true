package com.truedigital.vhealth.ui.login.phone;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.dialog.LoginOtpDialog;
import com.truedigital.vhealth.ui.login.otp.LoginOTPActivity;
import com.truedigital.vhealth.ui.login.password.LoginPasswordActivity;
import com.truedigital.vhealth.ui.setting.SettingActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.MyDialog;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginPhoneActivity extends BaseMvpActivity<LoginPhoneActivityInterface.Presenter>
        implements LoginPhoneActivityInterface.View {

    private EditText edPhone;
    private ImageView iv_next;
    private String reference_code;
    private String otp;
    private LoginOtpDialog loginOtpDialog;

    @Override
    protected LoginPhoneActivityInterface.Presenter createPresenter() {
        return LoginPhoneActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login_phone;
    }

    @Override
    protected void bindView() {
        edPhone = (EditText) findViewById(R.id.ed_login_phone);
        iv_next = (ImageView) findViewById(R.id.iv_next);
    }

    @Override
    protected void initInstance() {
        iv_next.setOnClickListener(onNextButtonClick());
    }

    private View.OnClickListener onNextButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onLoginButtonClick();
                //openUserSetting();
            }
        };
    }

    @Override
    protected void setupView() {
        loginOtpDialog = new LoginOtpDialog(this);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public String getPhoneNumber() {
        return edPhone.getText().toString();
    }

    @Override
    public void onErrorPhoneNumber() {
        edPhone.setError(getString(R.string.error_invalid_mobile));
        edPhone.requestFocus();
    }

    @Override
    public boolean isValid() {
        if (edPhone.getText().length() != 10) {
            onErrorPhoneNumber();
            return false;
        }

        return true;
    }

    @Override
    public String getOtp() {
        return otp;
    }

    @Override
    public String getReferenceCode() {
        return reference_code;
    }

    @Override
    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public void setReferenceCode(String reference_code) {
        this.reference_code = reference_code;
    }

    @Override
    public void openLoginWithPassword() {
        Intent intent = new Intent( this, LoginPasswordActivity.class );
        intent.putExtra(AppConstants.EXTRA_MOBILE_NO, getPhoneNumber());
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

    @Override
    public void openLoginWithOtp(String reference_code) {
        Intent intent = new Intent( this, LoginOTPActivity.class );
        intent.putExtra(AppConstants.EXTRA_MOBILE_NO, getPhoneNumber());
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }


    @Override
    public void openSignupSuccess() {
        new MyDialog(this).showSignupSuccess(new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                /*TODO: - Login and gotoHome
                *   - Login
                *   - goto Home with doctor isVerify
                * */
                openUserSetting();
            }

            @Override
            public void onClickCancel() {

            }
        });
    }

    @Override
    public void openUserSetting() {
        Intent intent = new Intent( this, SettingActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(AppConstants.EXTRA_SIGNUP_MODE,true);
        intent.putExtra(AppConstants.EXTRA_SIGNUP_REFERENC_CODE,getReferenceCode());
        intent.putExtra(AppConstants.EXTRA_SIGNUP_OTP,getOtp());
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

    /*
    @Override
    public void openMainActivity() {
        Intent intent = new Intent( this, MainActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

    @Override
    public void openForgotPassword() {
        Intent intent = new Intent( this, ForgotPasswordActivity.class );
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

    @Override
    public void openSignup() {
        Intent intent = new Intent( this, SettingActivity.class );
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

    @Override
    public void openDoctor() {

    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

}
