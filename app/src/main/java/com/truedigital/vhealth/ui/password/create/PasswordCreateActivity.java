package com.truedigital.vhealth.ui.password.create;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.login.email.LoginEmailActivity;
import com.truedigital.vhealth.ui.signup.success.SignupSuccessActivity;
import com.truedigital.vhealth.utils.AppConstants;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class PasswordCreateActivity extends BaseMvpActivity<PasswordCreateActivityInterface.Presenter>
        implements PasswordCreateActivityInterface.View {

    private static final String TAG = PasswordCreateActivity.class.getName();
    private EditText edPassword;
    private EditText edPasswordConfirm;
    private Button btnDone;
    private String mUserName;
    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;

    @Override
    protected PasswordCreateActivityInterface.Presenter createPresenter() {
        return PasswordCreateActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_password_create;
    }

    @Override
    protected void bindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nestedScrollView = (NestedScrollView) findViewById(R.id.NestedScrollView);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edPasswordConfirm = (EditText) findViewById(R.id.edPasswordConfirm);
        btnDone = (Button) findViewById(R.id.btn_done);
    }

    @Override
    protected void initInstance() {
        mUserName = getIntent().getStringExtra(AppConstants.EXTRA_USERNAME);
    }

    @Override
    protected void setupView() {
        setupToolbar();

        edPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    nestedScrollView.smoothScrollTo(0,nestedScrollView.getBottom());
                }
            }
        });

        edPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nestedScrollView.smoothScrollTo(0,nestedScrollView.getBottom());
            }
        });

        edPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    nestedScrollView.smoothScrollTo(0,nestedScrollView.getBottom());
                }
            }
        });
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(null);
            }
        }
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
    public String getUserName() {
        return mUserName;
    }

    @Override
    public int getUserId() {
        return getIntent().getIntExtra("UserId",0);
    }

    @Override
    public String getEmail() {
        return getIntent().getStringExtra(AppConstants.EXTRA_EMAIL);
    }

    @Override
    public String getDeviceToken() {
        return getIntent().getStringExtra(AppConstants.EXTRA_TOKEN);
    }

    @Override
    public String getPassword() {
        return edPassword.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return edPasswordConfirm.getText().toString();
    }

    @Override
    public void onErrorPassword() {
        edPassword.setError(getString(R.string.error_password));
        edPassword.requestFocus();
    }

    @Override
    public void onErrorConfirmPassword() {
        edPasswordConfirm.setError(getString(R.string.error_password_confirmed));
        edPasswordConfirm.requestFocus();
    }

    @Override
    public boolean isValid() {
        if (edPassword.getText().length() < 6) {
            onErrorPassword();
            return false;
        }
        if (edPasswordConfirm.getText().length() < 6) {
            onErrorConfirmPassword();
            return false;
        }
        if (!edPassword.getText().toString().equals(edPasswordConfirm.getText().toString())) {
            onErrorConfirmPassword();
            return false;
        }
        return true;
    }

    @Override
    public void openSignupSuccessActivity() {
        Log.e(TAG, "PasswordSuccess");
        Intent intent = new Intent(this, SignupSuccessActivity.class);
        intent.putExtra("isPasswordSuccess", true);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void openLoginEmailActivity() {
        Intent intent = new Intent( this, LoginEmailActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

    public void DoneButtonClick(View view) {
        getPresenter().onGoliveButtonClick();
    }
}
