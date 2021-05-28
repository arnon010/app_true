package com.truedigital.vhealth.ui.setting.insurance.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.truedigital.vhealth.R
import com.truedigital.vhealth.ui.setting.insurance.model.Insurance

class CheckWithImageView: RecyclerView.Adapter<CheckWithImageView.ViewHolder>() {

    val context : Context? = null
    var name = arrayOf(
        "AIA",
        "Cigna"
    )
    var img = arrayOf(
        R.drawable.aia,
        R.drawable.cigna
    )

    var number = arrayOf(
        "xxx-xxx-xxx",
        "xxx-xxx-xxx"
    )

    var insurance = mutableListOf<Insurance>()
    val onItemClickListener:OnItemClickListener? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameInsurance: AppCompatTextView
        val numberInsurance: AppCompatTextView
        val imgInsurance: AppCompatImageView
        val checkView : AppCompatImageView

        init {
            nameInsurance = itemView.findViewById(R.id.tvInsuranceName)
            numberInsurance = itemView.findViewById(R.id.tvInsuranceNumber)
            imgInsurance = itemView.findViewById(R.id.imgInsuranceList)
            checkView = itemView.findViewById(R.id.ivCheck)

            itemView.setOnClickListener { v ->
                var position: Int = adapterPosition
                checkView.visibility = View.VISIBLE
                onItemClickListener?.onItemClick(v , position)
                val context = v.context
                val intent = Intent(context, InsuranceEditActivity::class.java)
                intent.putExtra("position",position.toString())
                intent.putExtra("image", img[position])
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.widget_setting_check_with_image, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nameInsurance.text = name[position]
        viewHolder.numberInsurance.text = number[position]
        viewHolder.imgInsurance.setImageResource(img[position])
    }

    override fun getItemCount(): Int {
        return name.size
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
