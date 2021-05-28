package com.truedigital.vhealth.ui.login.email;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.utils.CommonUtils;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginEmailActivity extends BaseMvpActivity<LoginEmailActivityInterface.Presenter>
        implements LoginEmailActivityInterface.View {

    private EditText edEmail;
    private ImageView iv_next;

    @Override
    protected LoginEmailActivityInterface.Presenter createPresenter() {
        return LoginEmailActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login_email;
    }

    @Override
    protected void bindView() {
        edEmail = (EditText) findViewById(R.id.ed_login_email);
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
            }
        };
    }

    @Override
    protected void setupView() {

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
    public String getEmail() {
        return edEmail.getText().toString();
    }

    @Override
    public void onErrorEmail() {
        edEmail.setError(getString(R.string.error_invalid_email));
        edEmail.requestFocus();
    }

    @Override
    public boolean isValid() {
        if (!CommonUtils.isEmailValid(getEmail())) {
            onErrorEmail();
            return false;
        }

        return true;
    }

    @Override
    public void openLoginWithPassword() {
        /*
        Intent intent = new Intent( this, LoginPasswordActivity.class );
        intent.putExtra(AppConstants.EXTRA_MOBILE_NO, getPhoneNumber());
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        */
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
