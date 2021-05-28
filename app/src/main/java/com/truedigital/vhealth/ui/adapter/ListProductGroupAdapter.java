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
import com.truedigital.vhealth.model.ItemProductGroupDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListProductGroupAdapter extends RecyclerView.Adapter<ListProductGroupAdapter.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemProductGroupDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ListProductGroupAdapter(Context context) {
        this.context = context;
    }

    public ListProductGroupAdapter(Context context, List<ItemProductGroupDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product_group, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

        /*
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_doctor, parent, false));

            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));

        }
        */
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ItemProductGroupDao data = listData.get(position);
        Glide.with(context)
                .load(data.getGroupImage()).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.imgProfile);


        holder.tvTitle.setText(data.getGroupName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if (listData != null && listData.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final TextView tvTitle;

        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.imageView);
            tvTitle = (TextView) v.findViewById(R.id.tv_name);
        }
    }

    public ItemProductGroupDao getData(int position) {
        return listData.get(position);
    }

    public List<ItemProductGroupDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemProductGroupDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onFavoriteClick(View view, int position);
    }

}