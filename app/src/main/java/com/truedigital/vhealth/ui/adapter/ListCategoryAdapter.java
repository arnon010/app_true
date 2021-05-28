package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemCategoryDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListCategoryAdapter extends RecyclerView.Adapter<ListCategoryAdapter.ViewHolder> {

    private Context context;
    private List<ItemCategoryDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private int position;

    public ListCategoryAdapter(Context context) {
        this.context = context;
    }

    public ListCategoryAdapter(Context context, List<ItemCategoryDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    public ListCategoryAdapter(Context context, int position) {
        this.context = context;
        this.position = position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ItemCategoryDao data = listData.get(position);
        Glide.with(context)
                .load(data.getLogoImage())
                .asBitmap()
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .into(holder.imgProfile);

        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(view, position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;

        public ViewHolder(View v) {
            super(v);
            imgProfile = v.findViewById(R.id.card_image);
        }
    }

    public List<ItemCategoryDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemCategoryDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void addListData(List<ItemCategoryDao> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public ItemCategoryDao getData(int position)
    {
        return listData.get(position);
    }

    public void setFavorite(int position, boolean isFavorite) {
        notifyDataSetChanged();
    }

    public boolean isFavorite(int position) {
        return true;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onFavoriteClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }
}
