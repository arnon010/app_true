package com.truedigital.vhealth.ui.login.password;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.password.forgot.ForgotPasswordActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.StringUtils;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginPasswordActivity extends BaseMvpActivity<LoginPasswordActivityInterface.Presenter>
        implements LoginPasswordActivityInterface.View {

    private EditText edPassword;
    private ImageView iv_next;
    private Button btn_forgot_password;

    @Override
    protected LoginPasswordActivityInterface.Presenter createPresenter() {
        return LoginPasswordActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login_password;
    }

    @Override
    protected void bindView() {
        edPassword = (EditText) findViewById(R.id.ed_login_password);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        btn_forgot_password = (Button) findViewById(R.id.btn_forgot_password);
    }

    @Override
    protected void initInstance() {
        iv_next.setOnClickListener(v -> getPresenter().onLoginButtonClick());
        btn_forgot_password.setOnClickListener(v -> openForgotPasswordActivity());
    }

    @Override
    protected void setupView() {
        StringUtils.setUnderline(btn_forgot_password);
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
        return getIntent().getStringExtra(AppConstants.EXTRA_MOBILE_NO);
    }

    @Override
    public String getPassword() {
        return edPassword.getText().toString();
    }

    @Override
    public void onErrorPassword() {
        edPassword.setError(getString(R.string.error_invalid_password));
        edPassword.requestFocus();
    }

    @Override
    public boolean isValid() {
        if (edPassword.getText().length() < 4) {
            onErrorPassword();
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
    }

    @Override
    public void openForgotPasswordActivity() {
        Intent intent = new Intent( this, ForgotPasswordActivity.class );
        intent.putExtra(AppConstants.EXTRA_MOBILE_NO, getPhoneNumber());
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

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
