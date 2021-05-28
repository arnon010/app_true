package com.truedigital.vhealth.ui.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemDoctorScheduleTimeDao;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListDoctorTimeSlotAdapter extends RecyclerView.Adapter<ListDoctorTimeSlotAdapter.ViewHolder> {

    private List<ItemDoctorScheduleTimeDao> listData = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;
    private boolean isSelect;
    private boolean isDoctorCalendar;
    public ListDoctorTimeSlotAdapter(Context context) {
        this.context = context;
    }

    public ListDoctorTimeSlotAdapter(Context context, boolean isDoctorCalendar) {
        this.context = context;
        this.isDoctorCalendar = isDoctorCalendar;
    }

    public ListDoctorTimeSlotAdapter(Context context, List<ItemDoctorScheduleTimeDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    public ListDoctorTimeSlotAdapter(Context context, List<ItemDoctorScheduleTimeDao> listData, boolean isDoctorCalendar) {
        this.context = context;
        this.listData = listData;
        this.isDoctorCalendar = isDoctorCalendar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeslot, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ItemDoctorScheduleTimeDao data = listData.get(position);

        if (isDoctorCalendar) {
            holder.tvDate.setText(data.getScheduleTime());
            holder.tvTime.setText(context.getString(R.string.appointment_time_available));
        }
        else {
            holder.tvDate.setText(ConvertDate.convertDateSimpleShow(data.getScheduleTime()));
            holder.tvTime.setText(ConvertDate.StringDateServiceFormatTime(data.getScheduleTime()));
        }

        holder.itemView.setSelected(data.isSelected());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            onItemClickListener.onItemClick(view, position);

            setSelectedItem(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvDate;
        private final TextView tvTime;

        public ViewHolder(View v) {
            super(v);
            tvDate = (TextView) v.findViewById(R.id.tv_date);
            tvTime = (TextView) v.findViewById(R.id.tv_time);
        }
    }


    public void setListData(List<ItemDoctorScheduleTimeDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public List<ItemDoctorScheduleTimeDao> getListData() {
        return listData;
    }

    public ItemDoctorScheduleTimeDao getData(int position) {
        return listData.get(position);
    }

    public void setSelectedItem(int position) {
        resetSelected();
        listData.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    private void resetSelected() {
        for(ItemDoctorScheduleTimeDao data : listData) {
            data.setSelected(false);
        }
    }

    public void clearSelected() {
        resetSelected();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
