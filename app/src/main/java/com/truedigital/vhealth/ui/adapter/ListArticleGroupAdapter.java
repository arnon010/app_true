package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemArticleGroupDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListArticleGroupAdapter extends RecyclerView.Adapter<ListArticleGroupAdapter.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemArticleGroupDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    int position;


    public ListArticleGroupAdapter(Context context) {
        this.context = context;
    }

    public ListArticleGroupAdapter(Context context, List<ItemArticleGroupDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article_group, parent, false);
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

        final ItemArticleGroupDao data = listData.get(position);

        holder.tvTitle.setText(data.getGroupName());
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

        private final TextView tvTitle;

        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
        }
    }

    public List<ItemArticleGroupDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemArticleGroupDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, ItemArticleGroupDao data);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
