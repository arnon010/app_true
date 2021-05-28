package com.truedigital.vhealth.ui.meeting.base;

import com.truedigital.vhealth.ui.base.BaseMvpActivity;

/**
 * Created by songkrit on 11/13/2018 AD.
 */

public class BaseCallActivity extends BaseMvpActivity<BaseCallActivityInterface.Presenter>
        implements BaseCallActivityInterface.View {

    @Override
    public void onStopCall() {

    }

    @Override
    public void onMicroPhoneSelect() {

    }

    @Override
    public void onCameraSwap() {

    }

    @Override
    public void onStopConfirm() {

    }

    @Override
    protected BaseCallActivityInterface.Presenter createPresenter() {
        return BaseCallActivityPresenter.create();
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
}