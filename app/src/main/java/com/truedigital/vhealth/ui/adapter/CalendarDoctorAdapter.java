package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.CalendarObject;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nilecon on 10/25/2016 AD.
 */

public class CalendarDoctorAdapter extends RecyclerView.Adapter<CalendarDoctorAdapter.ViewHolder> {

    private ArrayList<CalendarObject> calendarArrayList = new ArrayList<>();
    private int selectedPosition = -1;
    private Calendar calendarCurrent;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CalendarDoctorAdapter(Context context, ArrayList<CalendarObject> calendarArrayList) {
        this.context = context;
        this.calendarArrayList = calendarArrayList;
        calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(ConvertDate.convertDateFormat(new Date()));

        //setSelectedDate(calendarCurrent.get(Calendar.DAY_OF_MONTH));
        //Log.e("Calendar "," day "+calendarCurrent.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar_doctor_day_of_month, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CalendarObject calendarObject = calendarArrayList.get(position);

        holder.img_open_slot.setVisibility(View.INVISIBLE);

        if (calendarObject.getDate() == null) {
            holder.txtDayOfMonth.setVisibility(View.INVISIBLE);
        }
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calendarObject.getDate());
            holder.txtDayOfMonth.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            holder.img_open_slot.setVisibility(View.INVISIBLE);
            /*
            if (position == selectedPosition) {
                //if (calendarObject.isFree()) {
                    holder.txtDayOfMonth.setSelected(true);
                    //holder.txtDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.color_text_black));
                    holder.txtDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.color_text_black));
                //}
            } else {
                holder.txtDayOfMonth.setSelected(false);
                //holder.txtDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.color_text_black));
                holder.txtDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            }
            */


            if (calendarObject.isSelected()) {
                holder.txtDayOfMonth.setSelected(true);
                holder.txtDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.color_green));
            }
            else {
                holder.txtDayOfMonth.setSelected(false);
                holder.txtDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            }

            //if (calendarObject.isHasOpenSlot()) {
            //    holder.img_open_slot.setVisibility(View.VISIBLE);
            //} else {
            //    holder.img_open_slot.setVisibility(View.INVISIBLE);
            //}

            if (isFutureDate(calendarObject.getDate())) {
                if (calendarObject.isHasAvailableSlot()) {
                    holder.img_open_slot.setVisibility(View.VISIBLE);
                } else {
                    holder.img_open_slot.setVisibility(View.INVISIBLE);
                }
                //holder.txtDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            }
            else {
                holder.txtDayOfMonth.setSelected(false);
                holder.txtDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.color_gray_light_text));
                holder.img_open_slot.setVisibility(View.INVISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        if (isFutureDate(calendarObject.getDate())) {
                            setDateSelect(position, true);
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                }
            });


        }
    }

    //..Today or next Day
    private boolean isFutureDate(Date date) {
        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(ConvertDate.convertDateFormat(new Date()));
        /*
        Log.e("CALENDAR","ConvertDate " + ConvertDate.convertDateFormat(new Date()).toString());
        Log.e("CALENDAR","calendarObject.getDate " + calendarObject.getDate());
        Log.e("CALENDAR","calendarCurrent.getTime " + calendarCurrent.getTime());
        if (calendarObject.getDate().after(calendarCurrent.getTime()) ||
                calendarObject.getDate().equals(calendarCurrent.getTime())) {


        }
        */
        if (date.after(calendarCurrent.getTime()) || date.equals(calendarCurrent.getTime())) {
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return calendarArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDayOfMonth;
        ImageView img_open_slot;
        public ViewHolder(View v) {
            super(v);
            txtDayOfMonth = (TextView) v.findViewById(R.id.textviewDayOfMonth);
            img_open_slot = (ImageView) v.findViewById(R.id.img_open_slot);
        }
    }

    public void setDateSelect(int position, boolean isSelected) {
        if (selectedPosition != -1) {
            calendarArrayList.get(selectedPosition).setSelected(false);
        }
        calendarArrayList.get(position).setSelected(isSelected);
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public void setSelectedDate(int date) {
        Calendar calendar = Calendar.getInstance();
        int i = 0;
        for (CalendarObject calendarObject : calendarArrayList) {
            if (calendarObject.getDate() != null) {
                calendar.setTime(calendarObject.getDate());
                if (calendar.get(Calendar.DAY_OF_MONTH) == date) {
                    selectedPosition = i;
                    notifyDataSetChanged();
                }
            }
            i++;
        }
    }

    public boolean isOpenTimeSlot(int position) {
        return calendarArrayList.get(position).isHasOpenSlot();
    }

    public boolean isHasAvailableSlot(int position) {
        return calendarArrayList.get(position).isHasAvailableSlot();
    }

    public Date getSelectedDateToDate(int position) {
        return calendarArrayList.get(position).getDate();
    }

    public String getSelectedDate(int position) {
        String date = null;
//        if (selectedPosition >= 0) {
        try {
            CalendarObject calendarObject = calendarArrayList.get(position);
            date = ConvertDate.convertDateToString(calendarObject.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
        return date;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }
}
