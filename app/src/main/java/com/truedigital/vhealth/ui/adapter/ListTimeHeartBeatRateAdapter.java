package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.HeartBeatRateObject;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

public class ListTimeHeartBeatRateAdapter extends RecyclerView.Adapter<ListTimeHeartBeatRateAdapter.ViewHolder>  {

    private ArrayList<HeartBeatRateObject> dataArrayList;

    private Context context;
    private ListTimeHeartBeatRateAdapter.OnItemClickListener onItemClickListener;

    public ListTimeHeartBeatRateAdapter(Context context, ArrayList<HeartBeatRateObject> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    public ListTimeHeartBeatRateAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<HeartBeatRateObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ListTimeHeartBeatRateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_heart_beat_rate, parent, false);
        ListTimeHeartBeatRateAdapter.ViewHolder vh = new ListTimeHeartBeatRateAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListTimeHeartBeatRateAdapter.ViewHolder holder, final int position) {
        HeartBeatRateObject data = dataArrayList.get(position);

        holder.txtBPM.setText(data.getDisplayBPM());
        holder.txtTime.setText(ConvertDate.convertTimeToString(data.getRecordDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HeartBeatRateObject data = dataArrayList.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(HeartBeatRateObject data);
    }

    public void setOnClickListener(ListTimeHeartBeatRateAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtBPM;
        private TextView txtTime;

        public ViewHolder(View v) {
            super(v);
            txtBPM = (TextView) v.findViewById(R.id.txtBPM);
            txtTime = (TextView) v.findViewById(R.id.txtTime);
        }
    }
}
