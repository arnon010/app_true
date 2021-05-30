package com.truedigital.vhealth.ui.setting.testinsurance.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.FragmentTestInsuranceBinding
import com.truedigital.vhealth.ui.main.MainActivity
import com.truedigital.vhealth.ui.setting.testinsurance.adapter.InsuranceAdapter
import com.truedigital.vhealth.ui.setting.testinsurance.model.User

class TestInsuranceFragment : Fragment() {
    lateinit var binding: FragmentTestInsuranceBinding
    var isShowTitle: Boolean? = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_insurance, container, false)

        isShowTitle = arguments?.getBoolean("insuranceFromSetting", false)

        if (isShowTitle == true) {
            (requireActivity() as? MainActivity)?.showToolbar(resources.getString(R.string.insurance), true)
        } else {
            (requireActivity() as? MainActivity)?.hideToolbar()
        }

        val users = ArrayList<User>()
        users.add(User("AIA", "XXX-X-XXX", R.drawable.aia))
        users.add(User("Cigna", "XXX-X-XXX", R.drawable.cigna))

        binding.txtInsuranceSetting.visibility = if (isShowTitle == true) View.GONE else View.VISIBLE
        val insuranceAdapter = InsuranceAdapter(users)
        binding.rvMain.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMain.adapter = insuranceAdapter

        binding.btnUpdateProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), TestInsuranceHealthActivity::class.java))
        }

        return binding.root
    }
}
