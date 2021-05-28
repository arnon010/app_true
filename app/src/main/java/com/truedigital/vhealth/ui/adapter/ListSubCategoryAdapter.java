package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemSubCategoryDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListSubCategoryAdapter extends RecyclerView.Adapter<ListSubCategoryAdapter.ViewHolder> {

    private Context context;
    private List<ItemSubCategoryDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    int position;
    ArrayList<Integer> listImage = new ArrayList<>();

    public ListSubCategoryAdapter(Context context) {
        this.context = context;
    }

    public ListSubCategoryAdapter(Context context, List<ItemSubCategoryDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    public ListSubCategoryAdapter(Context context, int position) {
        this.context = context;
        this.position = position;
        setTempImage();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        if (this.position == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_2, parent, false);
        }


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    private void setTempImage() {
        listImage.add(R.drawable.ic_category_baby);
        listImage.add(R.drawable.ic_category_women);
        listImage.add(R.drawable.ic_category_skin);
        listImage.add(R.drawable.ic_category_bone);
        listImage.add(R.drawable.ic_category_age);
        listImage.add(R.drawable.ic_category_baby);
        listImage.add(R.drawable.ic_category_checkup);
        listImage.add(R.drawable.ic_category_medicine);
        listImage.add(R.drawable.ic_category_bone);
        listImage.add(R.drawable.ic_category_checkup);
        listImage.add(R.drawable.ic_category_medicine);
        listImage.add(R.drawable.ic_category_bone);
    }

    private int getImage(int position) {
        return listImage.get(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ItemSubCategoryDao data = listData.get(position);

        /*
        Glide.with(context)
                .load(data.getLogoImage()).asBitmap()
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .into(holder.imgProfile);
        */
        int resId = getImage(position);
        /*
        resId = R.drawable.ic_leaf;
        if (position == 0) resId = R.drawable.ic_category_baby;
        else if (position == 1) resId = R.drawable.ic_category_women;
        else if (position == 2) resId = R.drawable.ic_category_skin;
        */
        Glide.with(context)
                .load("")
                .error(resId)
                .placeholder(resId)
                .into(holder.imgProfile);

        holder.tvTitle.setText(data.getDescription());
        holder.tvSubTitle.setText("");

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

    public List<ItemSubCategoryDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemSubCategoryDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void addListData(List<ItemSubCategoryDao> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public ItemSubCategoryDao getData(int position)
    {
        return listData.get(position);
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
