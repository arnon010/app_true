package com.truedigital.vhealth.ui.setting.testinsurance

import com.truedigital.vhealth.BuildConfig.SERVER_BASE
import com.truedigital.vhealth.api.network.RetrofitClient
import com.truedigital.vhealth.ui.setting.testinsurance.repo.InsuranceApiService
import com.truedigital.vhealth.ui.setting.testinsurance.repo.InsuranceRepository
import com.truedigital.vhealth.ui.setting.testinsurance.viewmodel.TestInsuranceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TestInsuranceViewModel(get()) }
}

val repositoryModule = module {
    single { InsuranceRepository(get()) }
}

val apiServiceModule = module {
    single { RetrofitClient.provideRetrofit(SERVER_BASE) }
    single { RetrofitClient.provideApiService(get(), InsuranceApiService::class.java) }
}