package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemMedicineDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListMedicineAdapter extends RecyclerView.Adapter<ListMedicineAdapter.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemMedicineDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ListMedicineAdapter(Context context) {
        this.context = context;
    }

    public ListMedicineAdapter(Context context, List<ItemMedicineDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medicine, parent, false);
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
        ItemMedicineDao data = listData.get(position);

        holder.tv_name.setText(""+data.getProductName());
        holder.tv_quantity.setText(""+data.getQuantity());
        holder.tv_method.setText(""+data.getMethodDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
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
        private final TextView tv_name;
        private final TextView tv_quantity;
        private final TextView tv_method;

        public ViewHolder(View v) {
            super(v);

            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_quantity = (TextView) v.findViewById(R.id.tv_quantity);
            tv_method = (TextView) v.findViewById(R.id.tv_method);
        }
    }

    public ItemMedicineDao getData(int position) {
        return listData.get(position);
    }
    public List<ItemMedicineDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemMedicineDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        int amount = 0;
        for (ItemMedicineDao item : listData) {
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
