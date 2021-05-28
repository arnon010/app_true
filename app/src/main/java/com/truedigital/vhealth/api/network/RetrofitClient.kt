package com.truedigital.vhealth.api.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun provideRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> provideApiService(retrofit: Retrofit, apiServiceClass: Class<T>): T {
        return retrofit.create(apiServiceClass)
    }
}