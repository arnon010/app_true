package com.truedigital.vhealth.ui.splashscreen;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SplashScreenActivityPresenter extends BaseMvpPresenter<SplashScreenActivityInterface.View>
        implements SplashScreenActivityInterface.Presenter {

    public static SplashScreenActivityInterface.Presenter create() {
        return new SplashScreenActivityPresenter();
    }

}