package com.truedigital.vhealth.ui.payment.promptpay

import android.os.Parcelable
import com.truedigital.vhealth.model.appointment.ApiAppointmentResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppointmentUiModel(
        val aurhorizeUri: String? = null,
        val qrUri: String? = null,
        val amount: Double? = null
) : Parcelable {

    constructor(response: ApiAppointmentResponse, paymentAmount: Double?) : this(
            aurhorizeUri = response.aurhorizeUri,
            qrUri = response.qrUri,
            amount = paymentAmount
    )
}
