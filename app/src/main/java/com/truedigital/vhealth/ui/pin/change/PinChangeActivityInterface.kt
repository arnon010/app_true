package com.truedigital.vhealth.ui.pin.change

import com.truedigital.vhealth.ui.base.BaseMvpInterface

interface PinChangeActivityInterface {

    interface View : BaseMvpInterface.View {

        fun showSuccess()

    }

    interface Presenter : BaseMvpInterface.Presenter<View> {
        var pinOld: String
        var pinNew: String
        var pinNewConfirm: String

        val isChangePinButtonEnable: Boolean

        fun executeChangePin()
    }
}
