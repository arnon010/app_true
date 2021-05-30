package com.truedigital.vhealth.ui.home.patient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.truedigital.vhealth.R
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.model.ItemSubCategoryDao
import java.util.*

class HealthcareAdapter : RecyclerView.Adapter<HealthcareAdapter.ViewHolder>() {

    private var listData: MutableList<ItemSubCategoryDao> = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_carousel_item_healthcare, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
        holder.itemView.onSingleClick { onItemClickListener?.onItemClick(data) }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(data: ItemSubCategoryDao) {
            Glide.with(itemView.context)
                    .load(data.logoImage)
                    .asBitmap()
                    .placeholder(R.drawable.img_iph_defaultimg2x)
                    .into(itemView.findViewById(R.id.imageView))
            //Uncomment when can get data for .load(data.image)
//            Glide.with(itemView.context)
//                    .load(data.logoImage)
//                    .asBitmap()
//                    .placeholder(R.drawable.img_iph_defaultimg2x)
//                    .into(itemView.findViewById(R.id.imageInsuranceMini))
        }
    }

    fun setListData(listData: MutableList<ItemSubCategoryDao>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: ItemSubCategoryDao)
    }

    fun setOnClickListener(OnItemClickListener: OnItemClickListener?) {
        onItemClickListener = OnItemClickListener
    }
}
