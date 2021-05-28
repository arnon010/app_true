package com.truedigital.vhealth.ui.setting.testinsurance.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.truedigital.vhealth.R
import com.truedigital.vhealth.ui.setting.insurance.view.InsuranceEditActivity
import com.truedigital.vhealth.ui.setting.testinsurance.model.User
import io.intercom.com.bumptech.glide.Glide
import java.util.*

class RecyclerViewAdapter(var context: Activity, var userArrayList: ArrayList<User>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.widget_setting_check_with_image, parent, false)
        return RecyclerViewViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = userArrayList[position]
        val viewHolder = holder as RecyclerViewViewHolder
        viewHolder.txtView_title.text = user.title
        viewHolder.txtView_description.text = user.description
        Glide.with(context)
            .load(user.imgIcon)
            .into(viewHolder.imgView_icon)
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    internal inner class RecyclerViewViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imgView_icon: ImageView
        var txtView_title: TextView
        var txtView_description: TextView

        init {
            imgView_icon = itemView.findViewById(R.id.imgInsuranceList)
            txtView_title = itemView.findViewById(R.id.tvInsuranceName)
            txtView_description = itemView.findViewById(R.id.tvInsuranceNumber)

            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent = Intent(context, InsuranceEditActivity::class.java)
                intent.putExtra("position",position.toString())
                context.startActivity(intent)
            }
        }
    }
}