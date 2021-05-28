package com.truedigital.vhealth.ui.setting.testinsurance.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.WidgetSettingCheckWithImageBinding
import com.truedigital.vhealth.ui.setting.insurance.view.InsuranceEditActivity
import com.truedigital.vhealth.ui.setting.testinsurance.model.User
import io.intercom.com.bumptech.glide.Glide
import java.util.*

class InsuranceAdapter(private var userArrayList: ArrayList<User>) :
        RecyclerView.Adapter<InsuranceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsuranceAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: WidgetSettingCheckWithImageBinding = DataBindingUtil.inflate(layoutInflater, R.layout.widget_setting_check_with_image, parent, false)
        return ViewHolder(viewBinding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userArrayList[position], position)
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    inner class ViewHolder(var viewBinding: WidgetSettingCheckWithImageBinding, var context: Context) :
            RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(data: User, position: Int) {
            viewBinding.apply {
                user = data
                Glide.with(context)
                        .load(data.imgIcon)
                        .into(imgInsuranceList)

                cardView.setOnClickListener {
                    val intent = Intent(context, InsuranceEditActivity::class.java)
                    intent.putExtra("position", position.toString())
                    intent.putExtra("image", data.imgIcon)
                    context.startActivity(intent)
                }
            }
        }
    }
}