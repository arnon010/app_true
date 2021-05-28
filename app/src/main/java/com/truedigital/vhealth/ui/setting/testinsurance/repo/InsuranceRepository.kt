package com.truedigital.vhealth.ui.setting.testinsurance.repo

class InsuranceRepository(private val insuranceApiService: InsuranceApiService) {
    suspend fun getInsurance() = insuranceApiService.getInsurance()
}