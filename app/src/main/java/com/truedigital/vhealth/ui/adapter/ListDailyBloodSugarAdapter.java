package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.DailyBloodSugarObject;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

public class ListDailyBloodSugarAdapter extends RecyclerView.Adapter<ListDailyBloodSugarAdapter.ViewHolder>  {

    private ArrayList<DailyBloodSugarObject> dataArrayList;

    private Context context;
    private ListDailyBloodSugarAdapter.OnItemClickListener onItemClickListener;

    public ListDailyBloodSugarAdapter(Context context, ArrayList<DailyBloodSugarObject> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    public ListDailyBloodSugarAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<DailyBloodSugarObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ListDailyBloodSugarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_daily_blood_sugar, parent, false);
        ListDailyBloodSugarAdapter.ViewHolder vh = new ListDailyBloodSugarAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListDailyBloodSugarAdapter.ViewHolder holder, final int position) {
        DailyBloodSugarObject data = dataArrayList.get(position);

        holder.txtBloodGlucose.setText(data.getDisplayBloodGlucose());
        holder.txtDate.setText(ConvertDate.convertDateSimpleShow(data.getDailyDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailyBloodSugarObject data = dataArrayList.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(DailyBloodSugarObject data);
    }

    public void setOnClickListener(ListDailyBloodSugarAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtBloodGlucose;
        private TextView txtDate;

        public ViewHolder(View v) {
            super(v);
            txtBloodGlucose = (TextView) v.findViewById(R.id.txtBloodGlucose);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
        }
    }
}
