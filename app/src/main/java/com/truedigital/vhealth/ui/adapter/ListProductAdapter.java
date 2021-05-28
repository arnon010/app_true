package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemProductDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private boolean isHideCartButton;

    public ListProductAdapter(Context context) {
        this.context = context;
    }

    public ListProductAdapter(Context context, boolean isHideCartButton) {
        this.context = context;
        this.isHideCartButton = isHideCartButton;
    }

    public ListProductAdapter(Context context, List<ItemProductDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
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
        ItemProductDao data = listData.get(position);
        Glide.with(context)
                .load(data.getCoverImage()).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.imgProfile);

        String productName = data.getTitle();
        if (productName == null) {
            //.. key name from product recommended
            productName = data.getProductName();
        }
        holder.tvTitle.setText(productName);
        holder.tvDesc.setText(data.getShortDescription());

        if (data.getPromotionSellingPrice() != 0) {
            holder.tvPrice.setText(""+data.getPromotionSellingPrice());
            holder.tvNormalPrice.setText(""+data.getNormalSellingPrice());
            StringUtils.setStrikeline(holder.tvNormalPrice);

        }
        else {
            holder.tvPrice.setText(""+data.getNormalSellingPrice());
            holder.tvNormalPrice.setText("");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });

        holder.fabAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemAddCartClick(view, position);
                }
            }
        });

        /*
        if (isHideCartButton) {
            holder.fabAddCart.setVisibility(View.GONE);
        }
        else {
            holder.fabAddCart.setVisibility(View.VISIBLE);
        }
        */

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
        private final TextView tvDesc;
        private final TextView tvPrice;
        private final TextView tvNormalPrice;
        private final FloatingActionButton fabAddCart;

        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.card_image);
            tvTitle = (TextView) v.findViewById(R.id.card_title);
            tvDesc = (TextView) v.findViewById(R.id.card_description);
            tvPrice = (TextView) v.findViewById(R.id.card_price);
            tvNormalPrice = (TextView) v.findViewById(R.id.card_normal_price);
            fabAddCart = (FloatingActionButton) v.findViewById(R.id.fabAddCart);
        }
    }

    public ItemProductDao getData(int position) {
        return listData.get(position);
    }

    public List<ItemProductDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemProductDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onFavoriteClick(View view, int position);
        void onItemAddCartClick(View view, int position);
    }


    public int getTotalPrice(){
        int totalPrice = 0;
        int amount = 0;
        for (ItemProductDao item : listData) {
            if (item.getPromotionSellingPrice() > 0) {
                amount = item.getQuantity() * item.getPromotionSellingPrice();
            }
            else {
                amount = item.getQuantity() * item.getNormalSellingPrice();
            }
            totalPrice += amount;
        }
        return totalPrice;
    }

}
