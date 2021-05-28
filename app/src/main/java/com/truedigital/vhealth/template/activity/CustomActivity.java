package com.truedigital.vhealth.template.activity;

import android.os.Bundle;

import com.truedigital.vhealth.ui.base.BaseMvpActivity;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class CustomActivity extends BaseMvpActivity<CustomActivityInterface.Presenter>
        implements CustomActivityInterface.View{


    @Override
    protected CustomActivityInterface.Presenter createPresenter() {
        return CustomActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return 0;
    }

    @Override
    protected void bindView() {

    }

    @Override
    protected void initInstance() {

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
}
