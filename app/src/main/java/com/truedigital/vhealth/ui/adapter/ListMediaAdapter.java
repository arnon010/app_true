package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.MediaObject;

import java.util.ArrayList;

public class ListMediaAdapter extends RecyclerView.Adapter<ListMediaAdapter.ViewHolder> {

    private ArrayList<MediaObject> dataArrayList;

    private Context context;
    private ListMediaAdapter.OnItemClickListener onItemClickListener;

    public ListMediaAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<MediaObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ListMediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_media, parent, false);
        return new ListMediaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListMediaAdapter.ViewHolder holder, final int position) {
        MediaObject data = dataArrayList.get(position);
        holder.txtName.setText(data.getMediaName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaObject media = dataArrayList.get(position);
                onItemClickListener.onItemClick(media);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(MediaObject data);
    }

    public void setOnClickListener(ListMediaAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        public ViewHolder(View v) {
            super(v);
            txtName = (TextView) v.findViewById(R.id.txtName);
        }
    }

}
