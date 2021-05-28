package com.truedigital.vhealth.api.pin

import com.truedigital.vhealth.model.NormalResponseObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PATCH

interface PinService {

    @FormUrlEncoded
    @PATCH(SET_UP_PIN)
    fun setUpPin(
            @Field("PIN") pin: String,
            @Field("ConfirmPIN") pinConfirm: String,
    ): Call<NormalResponseObject>

    @FormUrlEncoded
    @PATCH(CHANGE_PIN)
    fun changePin(
            @Field("OldPin") pinOld: String,
            @Field("PIN") pinNew: String,
            @Field("ConfirmPIN") pinNewConfirm: String,
    ): Call<NormalResponseObject>
}

private const val SET_UP_PIN = "Account/setPIN"
private const val CHANGE_PIN = "Account/changepin"
