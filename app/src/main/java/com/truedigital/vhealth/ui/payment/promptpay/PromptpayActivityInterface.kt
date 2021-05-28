package com.truedigital.vhealth.ui.payment.promptpay

import com.truedigital.vhealth.ui.base.BaseMvpInterface

interface PromptpayActivityInterface {

    interface View : BaseMvpInterface.View {
        fun onPaymentSuccess()
    }

    interface Presenter : BaseMvpInterface.Presenter<View> {
        fun checkAppointmentPromptpay(url: String?)
    }
}
