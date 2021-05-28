package com.truedigital.vhealth.ui.meeting.base;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class BaseCallActivityInterface {
    public interface View extends BaseMvpInterface.View {
        void onStopCall();
        void onMicroPhoneSelect();
        void onCameraSwap();

        void onStopConfirm();
    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {

    }
}
