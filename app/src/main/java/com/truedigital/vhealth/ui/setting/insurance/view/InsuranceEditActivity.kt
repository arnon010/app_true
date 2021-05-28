package com.truedigital.vhealth.ui.setting.insurance.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.truedigital.vhealth.R


class InsuranceEditActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insurance_edit)

        var txtPosition = findViewById<AppCompatTextView>(R.id.txtPosition)
        var imgInsuranceEdit = findViewById<AppCompatImageView>(R.id.imgInsuranceEdit)
        val bundle: Bundle = intent.extras!!
        val positions : String? = bundle.getString("position")
        val image: Int = bundle.getInt("image")
        imgInsuranceEdit.setImageResource(image)
        val txtGoBack = findViewById<AppCompatTextView>(R.id.txtGoBack)
        txtGoBack.setOnClickListener {
            onBackPressed()
        }
        val txtDeleteInsurance = findViewById<AppCompatTextView>(R.id.txtDeleteInsurance)
        txtDeleteInsurance.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle(R.string.pin_forgot_otp__title_confirm_cancel)
            alertDialogBuilder.setPositiveButton(R.string.button_yes) { _, _ ->
                finish()
            }
            alertDialogBuilder.setNegativeButton(R.string.button_no) { _, _ ->
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}