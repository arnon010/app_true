package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ApiListSymptom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListSymptomTypeAdapter extends RecyclerView.Adapter<ListSymptomTypeAdapter.ViewHolder> {

    private ArrayList<ApiListSymptom.FilterList> listData = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ListSymptomTypeAdapter(Context context) {
        this.context = context;
    }

    public ListSymptomTypeAdapter(Context context, ArrayList<ApiListSymptom.FilterList> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_clinic, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ApiListSymptom.FilterList data = listData.get(position);

        Glide.with(context)
                .load(data.getIcon()).asBitmap()
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .into(holder.imgProfile);

        holder.tvTitle.setText(R.string.symptom_title);
        holder.tvSubTitle.setText(data.getTitle());
        if (data.isFavorite()) {
            holder.ivFavorite.setColorFilter(ContextCompat.getColor(context,R.color.color_red));
        }
        else {
            holder.ivFavorite.setColorFilter(ContextCompat.getColor(context,R.color.color_gray));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });

        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onFavoriteClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final TextView tvTitle;
        private final TextView tvSubTitle;
        private final ImageView ivFavorite;

        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.card_image);
            tvTitle = (TextView) v.findViewById(R.id.card_title);
            tvSubTitle= (TextView) v.findViewById(R.id.card_text);
            ivFavorite = (ImageView) v.findViewById(R.id.favorite_button);
        }
    }

    public ArrayList<ApiListSymptom.FilterList> getListData() {
        return listData;
    }

    public void setListData(ArrayList<ApiListSymptom.FilterList> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void addListData(List<ApiListSymptom.FilterList> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public void setFavorite(int position, boolean isFavorite) {
        this.listData.get(position).setFavorite(isFavorite);
        notifyDataSetChanged();
    }

    public boolean isFavorite(int position) {
        return this.listData.get(position).isFavorite();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onFavoriteClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
