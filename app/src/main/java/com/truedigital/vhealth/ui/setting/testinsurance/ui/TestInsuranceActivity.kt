package com.truedigital.vhealth.ui.setting.testinsurance.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.truedigital.vhealth.R
import com.truedigital.vhealth.api.network.Result
import com.truedigital.vhealth.databinding.ActivityTestInsuranceBinding
import com.truedigital.vhealth.ui.setting.testinsurance.adapter.InsuranceAdapter
import com.truedigital.vhealth.ui.setting.testinsurance.apiServiceModule
import com.truedigital.vhealth.ui.setting.testinsurance.model.User
import com.truedigital.vhealth.ui.setting.testinsurance.repositoryModule
import com.truedigital.vhealth.ui.setting.testinsurance.viewModelModule
import com.truedigital.vhealth.ui.setting.testinsurance.viewmodel.TestInsuranceViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.*

class TestInsuranceActivity : AppCompatActivity(), LifecycleOwner {
    private val insuranceViewModel: TestInsuranceViewModel by inject()
    lateinit var binding: ActivityTestInsuranceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_insurance)

        insuranceViewModel.getPopulate()

        insuranceViewModel.user.observe(this, Observer {
            when (it) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    val insuranceAdapter = InsuranceAdapter(it.data)
                    binding.rvMain.layoutManager = LinearLayoutManager(this)
                    binding.rvMain.adapter = insuranceAdapter
                }
                is Result.Error -> {

                }
            }
        })

        binding.btnUpdateProfile.setOnClickListener {

        }
    }
}
