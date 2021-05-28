package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ListItemObject;

import java.util.ArrayList;

public class ListAdapter<T> extends RecyclerView.Adapter<ListAdapter.ViewHolder>  {

    private ArrayList<T> dataArrayList;

    private Context context;
    private ListAdapter.OnItemClickListener onItemClickListener;

    public ListAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<T> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position) {
        ListItemObject data = (ListItemObject)dataArrayList.get(position);
        holder.txtDescriptions.setText(data.getText());

        if(data.getIsShowImageNext() == true)
        {
            holder.imgNext.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.imgNext.setVisibility(View.INVISIBLE);
        }

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
        private ImageView imgNext;

        public ViewHolder(View v) {
            super(v);
            txtDescriptions = (TextView) v.findViewById(R.id.txtDescriptions);
            imgNext = (ImageView) v.findViewById(R.id.imgNext);
        }
    }
}
