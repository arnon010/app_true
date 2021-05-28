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
import com.truedigital.vhealth.model.RecommendProductObject;
import com.truedigital.vhealth.utils.GlideConfig;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ListRecommendProductAdapter extends RecyclerView.Adapter<ListRecommendProductAdapter.ViewHolder> {

    private Context context;
    private List<RecommendProductObject> listData = new ArrayList<>();
    private ListRecommendProductAdapter.OnConfirmClickListener onConfirmClickListener;

    public ListRecommendProductAdapter(Context context) {
        this.context = context;
    }

    public void setListData(List<RecommendProductObject> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public ListRecommendProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recommend_product, parent, false);
        ListRecommendProductAdapter.ViewHolder vh = new ListRecommendProductAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListRecommendProductAdapter.ViewHolder holder, final int position) {
        RecommendProductObject data = listData.get(position);

        Glide.with(context)
                .load(GlideConfig.getGlideHeader(data.getImageUrl())).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.imgProduct);

        holder.txtTitle.setText(data.getTitle());
        holder.txtDescriptions.setText(data.getShortDescription());
        holder.txtPrice.setText(String.format(context.getResources().getString(R.string.money_baht_double), data.getNormalSellingPrice()));
        holder.txtDiscountPrice.setText(String.format(context.getResources().getString(R.string.money_baht_double), data.getPromotionSellingPrice()));

        if (data.getPromotionSellingPrice() != 0) {
            StringUtils.setStrikeline(holder.txtPrice);
            holder.txtDiscountPrice.setVisibility(View.VISIBLE);
        } else {
            holder.txtDiscountPrice.setVisibility(View.INVISIBLE);
        }

        if(position == getItemCount() - 1)
        {
            holder.btnImgBuyProduct.setVisibility(View.VISIBLE);
            holder.btnImgBuyProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onConfirmClickListener.onConfirmClick();
                }
            });
        }
        else
        {
            holder.btnImgBuyProduct.setVisibility(View.INVISIBLE);
        }
    }

    public interface OnConfirmClickListener {
        void onConfirmClick();
    }

    public void setOnClickListener(ListRecommendProductAdapter.OnConfirmClickListener OnConfirmClickListener) {
        this.onConfirmClickListener = OnConfirmClickListener;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView txtTitle;
        private TextView txtDescriptions;
        private TextView txtPrice;
        private TextView txtDiscountPrice;
        private ImageView btnImgBuyProduct;

        public ViewHolder(View v) {
            super(v);
            imgProduct = (ImageView) v.findViewById(R.id.card_image);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtDescriptions = (TextView) v.findViewById(R.id.txtDescriptions);
            txtPrice = (TextView) v.findViewById(R.id.txtPrice);
            txtDiscountPrice = (TextView) v.findViewById(R.id.txtDiscountPrice);
            btnImgBuyProduct = (ImageView) v.findViewById(R.id.imgBuyProduct);

        }
    }
}
