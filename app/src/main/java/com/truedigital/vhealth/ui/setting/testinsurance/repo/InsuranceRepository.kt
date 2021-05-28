package com.truedigital.vhealth.ui.setting.testinsurance.repo

class InsuranceRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllMovies() = retrofitService.getAllMovies()
}