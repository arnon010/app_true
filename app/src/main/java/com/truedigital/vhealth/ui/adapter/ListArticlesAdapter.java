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
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListArticlesAdapter extends RecyclerView.Adapter<ListArticlesAdapter.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemArticleDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    int position;


    public ListArticlesAdapter(Context context) {
        this.context = context;
    }

    public ListArticlesAdapter(Context context, List<ItemArticleDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ItemArticleDao data = listData.get(position);

        Glide.with(context)
                .load(data.getCoverImage()).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.imgProfile);


        holder.tvTitle.setText(data.getTitle());
        if (data.getEffectiveDate() == null) {
            holder.tvTime.setText("");
        }
        else {
            holder.tvTime.setText(ConvertDate.StringDateServiceFormat(data.getEffectiveDate()));
        }
        holder.tvName.setText(data.getDoctorName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position, data);
                }
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
        private final TextView tvSubTitle;
        private final ImageView ivFavorite;
        private final TextView tvTime;
        private final TextView tvName;

        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.card_image);
            tvTitle = (TextView) v.findViewById(R.id.card_title);
            tvSubTitle= (TextView) v.findViewById(R.id.card_text);
            ivFavorite = (ImageView) v.findViewById(R.id.favorite_button);
            tvTime = (TextView) v.findViewById(R.id.card_time);
            tvName = (TextView) v.findViewById(R.id.card_name);
        }
    }


    public ItemArticleDao getData(int position ){
        return listData.get(position);
    }

    public List<ItemArticleDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemArticleDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, ItemArticleDao data);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
