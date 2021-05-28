package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.DailyBloodPressureObject;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

public class ListDailyBloodPressureAdapter extends RecyclerView.Adapter<ListDailyBloodPressureAdapter.ViewHolder>  {

    private ArrayList<DailyBloodPressureObject> dataArrayList;

    private Context context;
    private ListDailyBloodPressureAdapter.OnItemClickListener onItemClickListener;

    public ListDailyBloodPressureAdapter(Context context, ArrayList<DailyBloodPressureObject> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    public ListDailyBloodPressureAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<DailyBloodPressureObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ListDailyBloodPressureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_daily_blood_pressure, parent, false);
        ListDailyBloodPressureAdapter.ViewHolder vh = new ListDailyBloodPressureAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListDailyBloodPressureAdapter.ViewHolder holder, final int position) {
        DailyBloodPressureObject data = dataArrayList.get(position);

        holder.txtBPM.setText(data.getDisplayPressure());
        holder.txtDate.setText(ConvertDate.convertDateSimpleShow(data.getDailyDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailyBloodPressureObject data = dataArrayList.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(DailyBloodPressureObject data);
    }

    public void setOnClickListener(ListDailyBloodPressureAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtBPM;
        private TextView txtDate;

        public ViewHolder(View v) {
            super(v);
            txtBPM = (TextView) v.findViewById(R.id.txtBPM);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
        }
    }
}
