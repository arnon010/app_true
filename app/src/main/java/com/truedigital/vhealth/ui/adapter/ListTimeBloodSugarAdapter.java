package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

public class ListTimeBloodSugarAdapter extends RecyclerView.Adapter<ListTimeBloodSugarAdapter.ViewHolder>  {

    private ArrayList<BloodSugarObject> dataArrayList;

    private Context context;
    private ListTimeBloodSugarAdapter.OnItemClickListener onItemClickListener;

    public ListTimeBloodSugarAdapter(Context context, ArrayList<BloodSugarObject> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    public ListTimeBloodSugarAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<BloodSugarObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ListTimeBloodSugarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_blood_sugar, parent, false);
        ListTimeBloodSugarAdapter.ViewHolder vh = new ListTimeBloodSugarAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListTimeBloodSugarAdapter.ViewHolder holder, final int position) {
        BloodSugarObject data = dataArrayList.get(position);

        holder.txtBloodGlucose.setText(data.getDisplayBloodGlucose());
        holder.txtTime.setText(ConvertDate.convertTimeToString(data.getRecordDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BloodSugarObject data = dataArrayList.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(BloodSugarObject data);
    }

    public void setOnClickListener(ListTimeBloodSugarAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtBloodGlucose;
        private TextView txtTime;

        public ViewHolder(View v) {
            super(v);
            txtBloodGlucose = (TextView) v.findViewById(R.id.txtBloodGlucose);
            txtTime = (TextView) v.findViewById(R.id.txtTime);
        }
    }
}
