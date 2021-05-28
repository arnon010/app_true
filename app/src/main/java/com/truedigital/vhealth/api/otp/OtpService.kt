package com.truedigital.vhealth.api.otp

import com.truedigital.vhealth.model.NormalResponseObject
import com.truedigital.vhealth.model.pin.ApiPinRequestOtpObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OtpService {

    @POST(REQUEST_OTP)
    fun requestOtp(): Call<ApiPinRequestOtpObject>

    @FormUrlEncoded
    @POST(VERIFY_OTP)
    fun verifyOtp(
            @Field("ReferenceCode") referenceCode: String,
            @Field("OTP") otp: String,
    ): Call<NormalResponseObject>
}

private const val REQUEST_OTP = "api/otp/pincode/reset"
private const val VERIFY_OTP = "api/otp/pincode/verify"
