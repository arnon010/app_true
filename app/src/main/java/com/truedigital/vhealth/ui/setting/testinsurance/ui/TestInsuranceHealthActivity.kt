package com.truedigital.vhealth.ui.setting.testinsurance.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivityTestInsuranceHealthBinding
import com.truedigital.vhealth.ui.setting.testinsurance.adapter.InsuranceHealthAdapter


class TestInsuranceHealthActivity : AppCompatActivity(), LifecycleOwner {
    //    private val insuranceViewModel: TestInsuranceViewModel by inject()
    lateinit var binding: ActivityTestInsuranceHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_insurance_health)

        var list = ArrayList<Int>()
        list.add(R.drawable.aia)
        list.add(R.drawable.cigna)
        list.add(R.drawable.aia)
        list.add(R.drawable.cigna)
        list.add(R.drawable.cigna)
        list.add(R.drawable.cigna)

        val insuranceHealthAdapter = InsuranceHealthAdapter(list)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = insuranceHealthAdapter

        binding.txtSetUpInsurance.paintFlags = binding.txtSetUpInsurance.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.nestedScrollView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.nestedScrollView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = binding.nestedScrollView.width
                val height = binding.nestedScrollView.width
                binding.nestedScrollView.layoutParams.apply {
                    this.height = height
                    this.width = width
                }
            }
        })
    }
}
