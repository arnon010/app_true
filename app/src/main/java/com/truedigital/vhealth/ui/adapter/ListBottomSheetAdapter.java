package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BottomSheetObject;

import java.util.ArrayList;

/**
 * Created by warinthorn_s on 5/22/2018.
 */

public class ListBottomSheetAdapter<T> extends RecyclerView.Adapter<ListBottomSheetAdapter.ViewHolder>  {

    private ArrayList<T> dataArrayList;

    private Context context;
    private ListBottomSheetAdapter.OnItemClickListener onItemClickListener;

    public ListBottomSheetAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<T> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ListBottomSheetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buttom_sheet, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListBottomSheetAdapter.ViewHolder holder, final int position) {
        BottomSheetObject data = (BottomSheetObject)dataArrayList.get(position);
        holder.txtDescriptions.setText(data.getDescriptions());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T data = dataArrayList.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T data);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDescriptions;

        public ViewHolder(View v) {
            super(v);
            txtDescriptions = (TextView) v.findViewById(R.id.txtDescriptions);
        }
    }
}
