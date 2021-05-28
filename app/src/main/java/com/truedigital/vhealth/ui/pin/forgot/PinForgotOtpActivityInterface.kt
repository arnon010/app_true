package com.truedigital.vhealth.ui.pin.forgot

import com.truedigital.vhealth.ui.base.BaseMvpInterface

interface PinForgotOtpActivityInterface {

    interface View : BaseMvpInterface.View {

        fun navigateToSetPin()

        fun showRefCode(refCode: String)
    }

    interface Presenter : BaseMvpInterface.Presenter<View> {
        var phoneNumber: String
        var otp: String

        val isSubmitButtonEnable: Boolean

        fun executeRequestOtp()
        fun executeVerifyOtp()
    }
}
