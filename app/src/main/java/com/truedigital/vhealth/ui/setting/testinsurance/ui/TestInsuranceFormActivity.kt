package com.truedigital.vhealth.ui.setting.testinsurance.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.truedigital.vhealth.R
import com.truedigital.vhealth.api.network.Result
import com.truedigital.vhealth.databinding.ActivityInsuranceFormBinding
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

class TestInsuranceFormActivity : AppCompatActivity(), LifecycleOwner {
    lateinit var binding: ActivityInsuranceFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurance_form)


        binding.btnContinue.setOnClickListener {
            showDialog("title", "detail")

        }
    }

    @SuppressLint("ResourceAsColor")
    private fun showDialog(msg1: String, msg2: String) {
        val dialog = Dialog(this, R.style.roundCornerDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.custom_insurance_dialog)

        val txtTitle1 = dialog.findViewById<TextView>(R.id.txtTitleDialog1)
        val txtTitle2 = dialog.findViewById<TextView>(R.id.txtTitleDialog2)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnAccept = dialog.findViewById<Button>(R.id.btnAccept)

        txtTitle1.text = msg1
        txtTitle1.setTypeface(txtTitle1.typeface, Typeface.BOLD_ITALIC)
        txtTitle1.setTextColor(R.color.black)
        txtTitle2.text = msg2
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnAccept.setOnClickListener {
            dialog.dismiss()
        }

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER

        dialog.window!!.attributes = lp

        dialog.show()
    }
}
