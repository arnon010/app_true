package com.truedigital.vhealth.ui.setting.testinsurance.repo

import com.truedigital.vhealth.ui.setting.testinsurance.model.User
import retrofit2.Response
import retrofit2.http.GET


interface InsuranceApiService {

    @GET("/endpoint")
    suspend fun getInsurance(): Response<ArrayList<User>>
}