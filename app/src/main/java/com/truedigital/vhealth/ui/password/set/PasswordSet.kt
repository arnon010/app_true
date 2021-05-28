package com.truedigital.vhealth.ui.password.set

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PasswordSet(
        val refCode: String = "",
        val otp: String = ""
) : Parcelable
