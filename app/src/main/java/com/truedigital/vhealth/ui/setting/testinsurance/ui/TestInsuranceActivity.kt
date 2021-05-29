package com.truedigital.vhealth.ui.setting.testinsurance.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.truedigital.vhealth.R
import com.truedigital.vhealth.api.network.Result
import com.truedigital.vhealth.databinding.ActivityTestInsuranceBinding
import com.truedigital.vhealth.ui.setting.testinsurance.adapter.InsuranceAdapter
import com.truedigital.vhealth.ui.setting.testinsurance.viewmodel.TestInsuranceViewModel
import org.koin.android.ext.android.inject

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
            startActivity(Intent(this, TestInsuranceHealthActivity::class.java))
        }
    }
}
