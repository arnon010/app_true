package com.truedigital.vhealth.ui.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemClinicDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListClinicAdapter extends RecyclerView.Adapter<ListClinicAdapter.ViewHolder> {

    private List<ItemClinicDao> listData = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;
    int position;

    public ListClinicAdapter(Context context) {
        this.context = context;
    }

    public ListClinicAdapter(Context context, List<ItemClinicDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    public ListClinicAdapter(Context context, int position) {
        this.context = context;
        this.position = position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        //if (this.position == 1) {
        //    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinic_2, parent, false);
        //}


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ItemClinicDao data = listData.get(position);

        Glide.with(context)
                .load(data.getLogoImage()).asBitmap()
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .into(holder.imgProfile);

        Log.e("list clinic ",position + " " + data.getId() + " " + data.getTitle());

        /*
        Glide.with(context)
                .load(data.getLogoImageWithoutCaption()).asBitmap()
                .error(R.drawable.ic_profile_user)
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .into(holder.imgProfile);

        holder.tvTitle.setText(data.getTitle());
        holder.tvSubTitle.setText("");
        */
        /*
        if (data.isFavorite()) {
            holder.ivFavorite.setColorFilter(ContextCompat.getColor(context,R.color.color_red));
        }
        else {
            holder.ivFavorite.setColorFilter(ContextCompat.getColor(context,R.color.color_gray));
        }
        */

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });
        /*
        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onFavoriteClick(view, position);
            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        //private final TextView tvTitle;
        //private final TextView tvSubTitle;
        //private final ImageView ivFavorite;

        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.card_image);
            //tvTitle = (TextView) v.findViewById(R.id.card_title);
            //tvSubTitle= (TextView) v.findViewById(R.id.card_text);
            //ivFavorite = (ImageView) v.findViewById(R.id.favorite_button);
        }
    }

    public ItemClinicDao getData(int position) {
        return listData.get(position);
    }

    public List<ItemClinicDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemClinicDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void addListData(List<ItemClinicDao> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public void setFavorite(int position, boolean isFavorite) {
        //this.listData.get(position).setFavorite(isFavorite);
        notifyDataSetChanged();
    }

    public boolean isFavorite(int position) {
        return true; //this.listData.get(position).isFavorite();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onFavoriteClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
