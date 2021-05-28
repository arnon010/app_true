package com.truedigital.vhealth.model.pin

import com.truedigital.vhealth.model.NormalResponseObject
import com.google.gson.annotations.SerializedName

data class ApiPinRequestOtpObject(
        @SerializedName("ReferenceCode") val referenceCode: String? = null
) : NormalResponseObject()
