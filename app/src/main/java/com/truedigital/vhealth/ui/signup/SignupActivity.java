package com.truedigital.vhealth.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.signup.success.SignupSuccessActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SignupActivity extends BaseMvpActivity<SignupActivityInterface.Presenter>
        implements SignupActivityInterface.View{

    private EditText edUserName;
    private EditText edEmail;
    private Button btnDone;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private NestedScrollView nestedScrollView;
    private boolean isScroll;
    private boolean appBarExpanded;

    @Override
    protected SignupActivityInterface.Presenter createPresenter() {
        return SignupActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_signup;
    }

    @Override
    protected void bindView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nestedScrollView = (NestedScrollView) findViewById(R.id.NestedScrollView);

        edUserName = (EditText) findViewById(R.id.edUserName);
        edEmail = (EditText) findViewById(R.id.edEmail);
        btnDone = (Button) findViewById(R.id.btn_done);
    }


    @Override
    protected void initInstance() {

    }

    @Override
    protected void setupView() {
        setupToolbar();

        edUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                appBarLayout.setExpanded(false,true);
            }
            }
        });

        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    appBarLayout.setExpanded(false,true);
                    nestedScrollView.smoothScrollTo(0,200);
                }
            }
        });

        edUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appBarLayout.setExpanded(false,true);
                nestedScrollView.smoothScrollTo(0,200);
            }
        });

    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                //getSupportActionBar().setIcon(R.drawable.ic_favorite);
                getSupportActionBar().setTitle(null);
            }

            //collapsingToolbar.setTitle(getString(R.string.app_name));
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (Math.abs(verticalOffset) > AppConstants.APPBAR_OFFSET_SIZE) {
                        appBarExpanded = false;
                        invalidateOptionsMenu();
                    } else {
                        appBarExpanded = true;
                        invalidateOptionsMenu();
                    }
                    Log.d("verticalOffset ","" + Math.abs(verticalOffset));
                }
            });
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
        return edUserName.getText().toString();
    }

    @Override
    public String getEmail() {
        return edEmail.getText().toString();
    }

    @Override
    public void onErrorUserName() {
        edUserName.setError(getString(R.string.error_invalid_username));
        edUserName.requestFocus();
    }

    @Override
    public void onErrorEmail() {
        edEmail.setError(getString(R.string.error_invalid_email));
        edEmail.requestFocus();
    }

    @Override
    public boolean isValid() {
        if (edUserName.getText().length() < 6) {
            onErrorUserName();
            return false;
        }
        if (!CommonUtils.isEmailValid(getEmail())) {
            onErrorEmail();
            return false;
        }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //collapsedMenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!appBarExpanded) {
            toolbar.setNavigationIcon(R.drawable.ic_action_app);
        } else {
            toolbar.setNavigationIcon(null);
            isScroll = false;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void DoneButtonClick(View view) {
        getPresenter().onDoneButtonClick();
    }
}
