package com.truedigital.vhealth.ui.pin.forgot

import com.truedigital.vhealth.ui.base.BaseMvpInterface

interface PinForgotSetPinActivityInterface {

    interface View : BaseMvpInterface.View {

        fun showSuccess()

    }

    interface Presenter : BaseMvpInterface.Presenter<View> {
        var pin: String
        var pinConfirm: String

        val isSubmitButtonEnable: Boolean

        fun executeSetPin()
    }
}
