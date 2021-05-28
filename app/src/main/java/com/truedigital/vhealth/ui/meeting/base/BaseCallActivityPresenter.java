package com.truedigital.vhealth.ui.meeting.base;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class BaseCallActivityPresenter extends BaseMvpPresenter<BaseCallActivityInterface.View>
        implements BaseCallActivityInterface.Presenter{

    public static BaseCallActivityInterface.Presenter create() {
        return new BaseCallActivityPresenter();
    }

}
