package com.truedigital.vhealth.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.login.LoginActivity;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.setting.testinsurance.KoinStart;
import com.truedigital.vhealth.ui.tutorial.TutorialActivity;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SplashScreenActivity extends BaseMvpActivity<SplashScreenActivityInterface.Presenter>
        implements SplashScreenActivityInterface.View{


    private static final long SPLASH_DELAY = 3000;
    private String userName;
    private String email;
    private String token;
    private String mCategory;

    @Override
    protected SplashScreenActivityInterface.Presenter createPresenter() {
        return SplashScreenActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void bindView() {

    }

    @Override
    protected void initInstance() {

        //userName = getIntent().getStringExtra(AppConstants.EXTRA_USERNAME);
        //email = getIntent().getStringExtra(AppConstants.EXTRA_EMAIL);
        //token = getIntent().getStringExtra(AppConstants.EXTRA_TOKEN);
        //mCategory = getIntent().getStringExtra(AppConstants.EXTRA_CATEGORY);
    }

    @Override
    protected void setupView() {
        gotoTargetActivity();
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

    private void delaySplashScreen( Runnable runnable ){
        final Handler handler = new Handler();
        handler.postDelayed( runnable, SPLASH_DELAY );
    }

    private void gotoTargetActivity() {
        delaySplashScreen(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                //intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                //intent = new Intent(SplashScreenActivity.this, SignupUploadActivity.class );

                if (!AppManager.getDataManager().isOpenApp()) {
                    intent = new Intent(SplashScreenActivity.this, TutorialActivity.class);
                }
                else {

                    if (AppManager.getDataManager().isLogin()) {
                        /*
                        if (AppManager.getDataManager().getStatus() == AppConstants.APP_STATUS_SIGNUP){
                            intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        }
                        else {
                            intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        }
                        */

                        intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                    } else {
                        intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    }
                }

                KoinStart.Companion.initKoin(getApplicationContext());

                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}

