package com.truedigital.vhealth.ui.signup.success;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SignupSuccessActivity extends BaseMvpActivity<SignupSuccessActivityInterface.Presenter>
        implements SignupSuccessActivityInterface.View {

    private static final String TAG = SignupSuccessActivity.class.getName();
    private static final long DELAY_TIME = 3000L;
    private boolean isPasswordSuccess;
    private TextView tvHeadTitle;
    private TextView tvSubTitle;
    private TextView tvFootTitle;
    private TextView tvFootSubTitle;
    private Button btnDone;
    private TextView tvEmailResend;
    private String userName;
    private String email;
    private LinearLayout layoutEmailResend;
    private Toolbar toolbar;

    @Override
    protected SignupSuccessActivityInterface.Presenter createPresenter() {
        return SignupSuccessActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_signup_success;
    }

    @Override
    protected void bindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvHeadTitle = (TextView) findViewById(R.id.tvHeadTitle);
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
        tvFootTitle = (TextView) findViewById(R.id.tvFootTitle);
        tvFootSubTitle = (TextView) findViewById(R.id.tvFootSubTitle);
        tvEmailResend = (TextView) findViewById(R.id.tvResendEmail);
        layoutEmailResend = (LinearLayout) findViewById(R.id.layoutResendEmail);
        btnDone = (Button) findViewById(R.id.btn_done);
    }

    @Override
    protected void initInstance() {
        isPasswordSuccess = getIntent().getBooleanExtra("isPasswordSuccess",false);
        userName = getIntent().getStringExtra(AppConstants.EXTRA_USERNAME);
        email = getIntent().getStringExtra(AppConstants.EXTRA_EMAIL);
    }

    @Override
    protected void setupView() {
        Log.e(TAG,"Password " + isPasswordSuccess);
        setupToolbar();

        if (isPasswordSuccess) {
            tvHeadTitle.setText(getString(R.string.signup_success_head_title_confirm));
            tvSubTitle.setText(getString(R.string.signup_success_subtitle_confirm));
            tvFootTitle.setText(getString(R.string.signup_success_msg1_confirm));
            tvFootSubTitle.setText(getString(R.string.signup_success_msg2_confirm));
            tvEmailResend.setVisibility(View.INVISIBLE);
            layoutEmailResend.setVisibility(View.INVISIBLE);
        }
        else {
            btnDone.setBackground(ContextCompat.getDrawable(this,R.drawable.grey_light_rounded_shape));
        }
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                if (isPasswordSuccess) {
                    toolbar.setNavigationIcon(R.drawable.ic_action_app);
                }
                else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
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
        return userName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void openMainActivity() {

        delayScreen(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SignupSuccessActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
            }
        });
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

    private void delayScreen( Runnable runnable ){
        final Handler handler = new Handler();
        handler.postDelayed( runnable, DELAY_TIME );
    }

    public void ResenEmailClick(View view) {
        getPresenter().onResendEmailClick();
    }

    public void DoneButtonClick(View view) {
        if (isPasswordSuccess) {
            getPresenter().onDoneButtonClick();
        }

        //debug
        /*
        String userName = "test";
        String email = "test@gmail.com";
        String token = "111111111111111111111111";

        Intent intent = new Intent(this, PasswordCreateActivity.class);
        intent.putExtra("UserName", userName);
        intent.putExtra("Email", email);
        intent.putExtra("Token", token);
        startActivity(intent);
        */
    }
}
