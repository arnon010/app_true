package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemListCreditCardDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListCreditCardAdapter extends RecyclerView.Adapter<ListCreditCardAdapter.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemListCreditCardDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    int position;


    public ListCreditCardAdapter(Context context) {
        this.context = context;
    }

    public ListCreditCardAdapter(Context context, List<ItemListCreditCardDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_credit_card, parent, false);
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
        final ItemListCreditCardDao data = listData.get(position);

        holder.tv_credit_card.setText(data.getLastDigits());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position, data);
                    setSelected(position, !data.isSelected());
                }
            }
        });
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemRemove(view, position, data);
                }
            }
        });
        
        holder.img_select.setSelected(data.isSelected());

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
        private final TextView tv_credit_card;
        private final Button btn_remove;
        private final ImageView img_select;

        public ViewHolder(View v) {
            super(v);
            img_select = (ImageView) v.findViewById(R.id.img_select);
            tv_credit_card = (TextView) v.findViewById(R.id.tv_credit_card);
            btn_remove = (Button) v.findViewById(R.id.btn_remove);
        }
    }


    public ItemListCreditCardDao getData(int position ){
        return listData.get(position);
    }

    public List<ItemListCreditCardDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemListCreditCardDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        this.listData.remove(position);
        notifyDataSetChanged();
    }

    private void resetSelected() {
        for (int i=0; i<listData.size(); i++) {
            listData.get(i).setSelected(false);
        }
    }

    private void setSelected(int position, boolean selected) {
        resetSelected();

        listData.get(position).setSelected(selected);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, ItemListCreditCardDao data);
        void onItemRemove(View view, int position, ItemListCreditCardDao data);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
