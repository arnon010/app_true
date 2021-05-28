package com.truedigital.vhealth.ui.setting.testinsurance.repo

import io.intercom.android.sdk.models.User
import io.intercom.retrofit2.Call
import io.intercom.retrofit2.Retrofit
import io.intercom.retrofit2.converter.gson.GsonConverterFactory
import io.intercom.retrofit2.http.GET

interface RetrofitService {

    @GET("movielist.json")
    fun getAllMovies() : Call<List<User>>

    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://howtodoandroid.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}