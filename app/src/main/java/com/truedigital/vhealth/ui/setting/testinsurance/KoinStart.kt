package com.truedigital.vhealth.ui.setting.testinsurance

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinStart {
    companion object {
        fun initKoin(context: Context) {
            startKoin {
                modules(viewModelModule, repositoryModule, apiServiceModule)
                androidContext(context)
            }
        }
    }
}

