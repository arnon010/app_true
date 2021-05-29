package com.truedigital.vhealth.ui.setting.testinsurance.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivityInsuranceFormBinding

class TestInsuranceFormActivity : AppCompatActivity(), LifecycleOwner {
    lateinit var binding: ActivityInsuranceFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurance_form)
        val image = intent.getIntExtra("image", 0)
        binding.imgInsuranceForm.setImageResource(image)

        binding.btnContinue.setOnClickListener {
            showDialog("คุณสามารถใช้สิทธิประกันในการรับบริการ", "กดปุ่ม\n" + "*ดำเนินการต่อ* เพื่อยืนยันตัวตนของท่าน\n" + "หรือ\n" + "ยกเลิกการตั้งค่าประกัน\n" + "ซึ่งคุณสามารถทำการตั้งค่าภายหลังได้ในเมนู\n" + "*การตั้งค่า*")

        }

        binding.btnCancel.setOnClickListener { finish() }
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
        txtTitle1.setTypeface(txtTitle1.typeface, Typeface.BOLD)
        txtTitle1.setTextColor(R.color.black)
        txtTitle1.textSize = 16f
        txtTitle2.text = msg2
        txtTitle2.gravity = Gravity.LEFT
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
