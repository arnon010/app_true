package com.truedigital.vhealth.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;

import static com.truedigital.vhealth.api.RetrofitBuilder.addMultipartBody;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SettingActivity extends BaseMvpActivity<SettingActivityInterface.Presenter>
        implements SettingActivityInterface.View{


    private EditText edEmail;
    private Button btnDone;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private NestedScrollView nestedScrollView;
    private boolean isScroll;
    private boolean appBarExpanded;
    private ImageView card_image;
    private TextView tvLogin;

    private EditText edUserName;
    private EditText edPassword;
    private EditText edPasswordConfirm;
    private boolean signup_mode;
    private boolean forgot_password_mode;
    private Button btnAppInfo;
    private FloatingActionButton fabConfirm;
    private LinearLayout toolbarMain;
    private ImageButton ic_button_back;
    private TextView tv_toolbar_name;
    private String otp;
    private String reference_code;
    private String phoneNumber;

    @Override
    protected SettingActivityInterface.Presenter createPresenter() {
        return SettingActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void bindView() {
        bindViewToolbar();

        /*
        card_image = (ImageView) findViewById(R.id.card_image);
        tvLogin = (TextView) findViewById(R.id.tvLogin);

        edUserName = (EditText) findViewById(R.id.edUserName);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edPasswordConfirm = (EditText) findViewById(R.id.edPasswordConfirm);

        fabConfirm = (FloatingActionButton) findViewById(R.id.fabConfirm);
        btnAppInfo = (Button) findViewById(R.id.btn_appinfo);
        btnDone = (Button) findViewById(R.id.btn_confirm);
        */
    }

    private void bindViewToolbar() {
        toolbarMain = (LinearLayout) findViewById(R.id.toolbarMain);
        ic_button_back = (ImageButton) toolbarMain.findViewById(R.id.ic_button_back);
        tv_toolbar_name = (TextView) toolbarMain.findViewById(R.id.tvToolbarName);
        ic_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void showToolbar(int resId, boolean showHomeAsUp) {
        tv_toolbar_name.setText(resId);
        ic_button_back.setVisibility(showHomeAsUp ? View.VISIBLE : View.GONE);
        toolbarMain.setVisibility(View.VISIBLE);
        toolbarMain.findViewById(R.id.toolbar_image).setVisibility(View.GONE);
    }

    @Override
    protected void initInstance() {

    }

    @Override
    protected void setupView() {
        showToolbar(R.string.setting_activity_title,false);

        phoneNumber = getIntent().getStringExtra(AppConstants.EXTRA_MOBILE_NO);
        otp = getIntent().getStringExtra(AppConstants.EXTRA_SIGNUP_OTP);
        reference_code = getIntent().getStringExtra(AppConstants.EXTRA_SIGNUP_REFERENC_CODE);
        signup_mode = getIntent().getBooleanExtra(AppConstants.EXTRA_SIGNUP_MODE,false);
        forgot_password_mode = getIntent().getBooleanExtra(AppConstants.EXTRA_FORGOT_PASSWORD_MODE,false);

        updateView();
        /*

        signup_mode = getIntent().getBooleanExtra(AppConstants.EXTRA_SIGNUP_MODE,false);
        if (signup_mode) {
            fabConfirm.setVisibility(View.GONE);
            btnAppInfo.setVisibility(View.GONE);
            btnDone.setVisibility(View.VISIBLE);
        }
        else {
            fabConfirm.setVisibility(View.VISIBLE);
            btnAppInfo.setVisibility(View.VISIBLE);
            btnDone.setVisibility(View.GONE);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onDoneButtonClick();
            }
        });
        */
    }

    private void updateView() {
        addFragment(SettingFragment.newInstance(signup_mode,false,forgot_password_mode,reference_code,otp));
    }

    public void addFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.content_setting, fragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
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

    /*
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
    */

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
            //Log.e(TAG, "Fragment backStack " + getSupportFragmentManager().getBackStackEntryCount());
        } else {
            //super.onBackPressed();
            //finish();
            return;
        }
    }

    /*

    @Override
    public String getUserName() {
        return edUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return edPassword.getText().toString();
    }

    @Override
    public String getPasswordConfirm() {
        return edPasswordConfirm.getText().toString();
    }

    @Override
    public String getEmail() {
        return edEmail.getText().toString();
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
    public void onErrorUserName() {
        edUserName.setError(getString(R.string.error_invalid_username));
        edUserName.requestFocus();
    }

    @Override
    public void onErrorEmail() {
        //edEmail.setError(getString(R.string.error_invalid_email));
        //edEmail.requestFocus();
    }

    @Override
    public boolean isValid() {
        if (edUserName.getText().length() < 6) {
            onErrorUserName();
            return false;
        }
        //if (!CommonUtils.isEmailValid(getEmail())) {
        //    onErrorEmail();
        //    return false;
        //}

        return true;
    }


    @Override
    public void openSignupSuccessActivity() {
        Intent intent = new Intent( this, SignupSuccessActivity.class );
        intent.putExtra(AppConstants.EXTRA_USERNAME,getUserName());
        intent.putExtra(AppConstants.EXTRA_EMAIL,getEmail());
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }


    @Override
    public MultipartBody.Part getImageBody() {
        //MultipartBody.Part profileImage = addMultipartBody(this, "ProfileImage", uriImage);
        return null;
    }
    */
    @Override
    public void openMainActivity() {
        Intent intent = new Intent( this, MainActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        finish();
    }
}
