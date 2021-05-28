package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BloodPressureObject;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

public class ListTimeBloodPressureAdapter extends RecyclerView.Adapter<ListTimeBloodPressureAdapter.ViewHolder>  {

    private ArrayList<BloodPressureObject> dataArrayList;

    private Context context;
    private ListTimeBloodPressureAdapter.OnItemClickListener onItemClickListener;

    public ListTimeBloodPressureAdapter(Context context, ArrayList<BloodPressureObject> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    public ListTimeBloodPressureAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<BloodPressureObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ListTimeBloodPressureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_blood_pressure, parent, false);
        ListTimeBloodPressureAdapter.ViewHolder vh = new ListTimeBloodPressureAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListTimeBloodPressureAdapter.ViewHolder holder, final int position) {
        BloodPressureObject data = dataArrayList.get(position);

        holder.txtSysDia.setText(data.getDisplayPressure());
        holder.txtTime.setText(ConvertDate.convertTimeToString(data.getRecordDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BloodPressureObject data = dataArrayList.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(BloodPressureObject data);
    }

    public void setOnClickListener(ListTimeBloodPressureAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtSysDia;
        private TextView txtTime;

        public ViewHolder(View v) {
            super(v);
            txtSysDia = (TextView) v.findViewById(R.id.txtSysDia);
            txtTime = (TextView) v.findViewById(R.id.txtTime);
        }
    }
}
