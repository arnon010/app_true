package com.truedigital.vhealth.ui.adapter;


import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarDayOfWeekAdapter extends RecyclerView.Adapter<CalendarDayOfWeekAdapter.ViewHolder> {

    private ArrayList<Date> calendarArrayList = new ArrayList<>();
    private int selectedPosition = -1;
    private Context context;
    private int textColor;

    public CalendarDayOfWeekAdapter(Context context, ArrayList<Date> calendarArrayList) {
        this.context = context;
        this.calendarArrayList = calendarArrayList;
    }

    public CalendarDayOfWeekAdapter(Context context, ArrayList<Date> calendarArrayList, int textColor) {
        this.context = context;
        this.calendarArrayList = calendarArrayList;
        this.textColor = textColor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar_day_of_week, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendarArrayList.get(position));

        holder.txtDayOfWeek.setText(ConvertDate.getDayOfWeek(calendar.getTime()));
        if(this.textColor != 0)
        {
            holder.txtDayOfWeek.setTextColor(ContextCompat.getColor(this.context, this.textColor));
        }

    }

    @Override
    public int getItemCount() {
        return calendarArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDayOfWeek;

        public ViewHolder(View v) {
            super(v);
            txtDayOfWeek = (TextView) v.findViewById(R.id.textviewDayOfWeek);
        }
    }
}
