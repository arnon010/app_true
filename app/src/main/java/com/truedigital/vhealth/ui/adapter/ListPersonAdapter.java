package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemPersonDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListPersonAdapter extends RecyclerView.Adapter<ListPersonAdapter.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemPersonDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    int position;
    ArrayList<Integer> listImage = new ArrayList<>();
    private int selectedItem;

    public ListPersonAdapter(Context context) {
        this.context = context;
    }

    public ListPersonAdapter(Context context, List<ItemPersonDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_person, parent, false);
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


        ItemPersonDao data = listData.get(position);

        holder.tvTitle.setText(data.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            notifyItemChanged(selectedItem);
            selectedItem = position;
            notifyItemChanged(selectedItem);
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, position);
            }
            }
        });
        holder.itemView.setSelected(position == selectedItem);

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
            tvTitle = (TextView) v.findViewById(R.id.tv_name);
        }
    }

    public List<ItemPersonDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemPersonDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onFavoriteClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
