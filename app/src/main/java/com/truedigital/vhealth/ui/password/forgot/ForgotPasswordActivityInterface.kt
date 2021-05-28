package com.truedigital.vhealth.ui.password.forgot

import com.truedigital.vhealth.ui.base.BaseMvpInterface
import com.truedigital.vhealth.ui.password.set.PasswordSet

interface ForgotPasswordActivityInterface {

    interface View : BaseMvpInterface.View {

        fun navigateToSetPassword(data: PasswordSet)

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
