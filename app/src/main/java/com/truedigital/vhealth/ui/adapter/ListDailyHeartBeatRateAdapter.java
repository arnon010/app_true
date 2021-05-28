package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.DailyHeartBeatRateObject;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

public class ListDailyHeartBeatRateAdapter extends RecyclerView.Adapter<ListDailyHeartBeatRateAdapter.ViewHolder>  {

    private ArrayList<DailyHeartBeatRateObject> dataArrayList;

    private Context context;
    private ListDailyHeartBeatRateAdapter.OnItemClickListener onItemClickListener;

    public ListDailyHeartBeatRateAdapter(Context context, ArrayList<DailyHeartBeatRateObject> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    public ListDailyHeartBeatRateAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<DailyHeartBeatRateObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    public ArrayList<DailyHeartBeatRateObject> getListData() {
        return this.dataArrayList;
    }

    @Override
    public ListDailyHeartBeatRateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_daily_heart_beat_rate, parent, false);
        ListDailyHeartBeatRateAdapter.ViewHolder vh = new ListDailyHeartBeatRateAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListDailyHeartBeatRateAdapter.ViewHolder holder, final int position) {
        DailyHeartBeatRateObject data = dataArrayList.get(position);

        holder.txtRangeBPM.setText(data.getDisplayBPM());
        holder.txtDate.setText(ConvertDate.convertDateSimpleShow(data.getDailyDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailyHeartBeatRateObject data = dataArrayList.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(DailyHeartBeatRateObject data);
    }

    public void setOnClickListener(ListDailyHeartBeatRateAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtRangeBPM;
        private TextView txtDate;

        public ViewHolder(View v) {
            super(v);
            txtRangeBPM = (TextView) v.findViewById(R.id.txtRangeBPM);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
        }
    }
}
