package com.truedigital.vhealth.ui.setting.testinsurance.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ItemInsuramceHealthBinding
import com.truedigital.vhealth.ui.setting.testinsurance.ui.TestInsuranceFormActivity
import io.intercom.com.bumptech.glide.Glide
import java.util.*

class InsuranceHealthAdapter(private var listImage: ArrayList<Int>) :
        RecyclerView.Adapter<InsuranceHealthAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsuranceHealthAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: ItemInsuramceHealthBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_insuramce_health, parent, false)
        return ViewHolder(viewBinding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listImage[position], position)
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    inner class ViewHolder(var viewBinding: ItemInsuramceHealthBinding, var context: Context) :
            RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(img: Int, position: Int) {
            viewBinding.apply {
                Glide.with(context)
                        .load(img)
                        .into(imgInsuranceHealth)

                imgInsuranceHealth.setOnClickListener {
                    showDialog(context,
                            context.getString(R.string.text_insurance_health_1),
                            context.getString(R.string.text_insurance_health_2),
                            img)
                }

                handleSizeImage(imgInsuranceHealth, )

            }
        }
    }

    private fun handleSizeImage(image: ImageView) {
        image.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                image.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = image.width
                val height = image.width
                image.layoutParams.apply {
                    this.height = height
                    this.width = width
                }
            }
        })
    }

    private fun showDialog(context: Context, msg1: String, msg2: String, img: Int) {
        val dialog = Dialog(context, R.style.roundCornerDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.custom_insurance_dialog)

        val txtTitle1 = dialog.findViewById<TextView>(R.id.txtTitleDialog1)
        val txtTitle2 = dialog.findViewById<TextView>(R.id.txtTitleDialog2)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnAccept = dialog.findViewById<Button>(R.id.btnAccept)

        txtTitle1.text = msg1
        txtTitle2.text = msg2
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnAccept.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(context, TestInsuranceFormActivity::class.java)
            intent.putExtra("image", img)
            context.startActivity(intent)
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