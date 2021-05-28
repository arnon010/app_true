package com.truedigital.vhealth.ui.setting.insurance.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivityInsuranceEditBinding


class InsuranceEditActivity : AppCompatActivity() {

    lateinit var binding: ActivityInsuranceEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurance_edit)

        val bundle: Bundle = intent.extras!!
        val positions: String? = bundle.getString("position")
        val image: Int = bundle.getInt("image")
        binding.imgInsuranceEdit.setImageResource(image)

        binding.txtGoBack.setOnClickListener {
            finish()
        }

        binding.txtDeleteInsurance.setOnClickListener {
            showDialog("Do you want to delete {{Insurance}} ?")
        }


    }

    @SuppressLint("ResourceAsColor")
    private fun showDialog(msg: String) {
        val dialog = Dialog(this, R.style.roundCornerDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.custom_dialog)

        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitleDialog)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancelDialog)
        val btnSubmit = dialog.findViewById<Button>(R.id.btnSubmitDialog)

        txtTitle.text = msg
        txtTitle.setTextColor(R.color.black)
        btnCancel.text = "No"
        btnSubmit.text = "Yes"
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnSubmit.setOnClickListener {
            dialog.dismiss()
            finish()
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