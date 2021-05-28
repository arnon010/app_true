package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.CalendarObject;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private boolean isSelectMultiple = false;
    private ArrayList<CalendarObject> calendarArrayList;
    private int textColor;
    private int textBackground;
    private int selectedPosition = -1;
    private Calendar calendarCurrent;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CalendarAdapter(Context context, Calendar calendarCurrent, ArrayList<CalendarObject> calendarArrayList, boolean isSelectMultiple) {
        this.context = context;
        this.calendarArrayList = calendarArrayList;
        this.calendarCurrent = calendarCurrent;
        this.isSelectMultiple = isSelectMultiple;
    }

    public CalendarAdapter(Context context, Calendar calendarCurrent, ArrayList<CalendarObject> calendarArrayList, boolean isSelectMultiple, int textColor, int textBackground) {
        this.context = context;
        this.calendarArrayList = calendarArrayList;
        this.calendarCurrent = calendarCurrent;
        this.isSelectMultiple = isSelectMultiple;
        this.textColor = textColor;
        this.textBackground = textBackground;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar_day_of_month, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CalendarObject calendarObject = calendarArrayList.get(position);

        if (this.textColor != 0) {
            holder.txtDayOfMonth.setTextColor(this.context.getResources().getColorStateList(this.textColor));
        }

        if (this.textBackground != 0) {
            holder.txtDayOfMonth.setBackground(ContextCompat.getDrawable(this.context, this.textBackground));
        }

        if (calendarObject.getDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calendarObject.getDate());
            holder.txtDayOfMonth.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

            if (isSelectMultiple) {
                if (calendarObject.isActive()) {
                    if (calendarObject.isFree()) {
                        holder.txtDayOfMonth.setSelected(true);
                    }
                } else {
                    holder.txtDayOfMonth.setSelected(false);
                }
            } else {
                if (position == selectedPosition) {
                    if (calendarObject.isFree()) {
                        holder.txtDayOfMonth.setSelected(true);
                    }
                } else {
                    holder.txtDayOfMonth.setSelected(false);
                }
            }


            if (calendarObject.isFree()) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CalendarObject calendarObject = calendarArrayList.get(position);

                        if (isSelectMultiple) {
                            if (calendarObject.isActive() == false && calendarObject.isFree()) {
                                selectedPosition = position;
                                calendarObject.setActive(true);
                            } else {
                                selectedPosition = -1;
                                calendarObject.setActive(false);
                            }
                        } else {
                            selectedPosition = position;
                        }


                        notifyDataSetChanged();
                        onItemClickListener.onItemClick(view, position);
                    }
                });
            } else {
                holder.txtDayOfMonth.setTextColor(ContextCompat.getColor(context, R.color.color_gray_light_text));
            }

        } else {
            holder.txtDayOfMonth.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return calendarArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDayOfMonth;

        public ViewHolder(View v) {
            super(v);
            txtDayOfMonth = (TextView) v.findViewById(R.id.textviewDayOfMonth);
        }
    }

    public void setSelectedDate(int date) {
        Calendar calendar = Calendar.getInstance();
        int i = 0;
        for (CalendarObject calendarObject : calendarArrayList) {
            if (calendarObject.getDate() != null) {
                calendar.setTime(calendarObject.getDate());
                if (calendar.get(Calendar.DAY_OF_MONTH) == date) {
                    selectedPosition = i;
                    if (this.isSelectMultiple) {
                        calendarObject.setActive(true);
                    }
                    notifyDataSetChanged();
                }
            }
            i++;
        }
    }

    public String getSelectedDateString() {
        String date = null;
        if (selectedPosition >= 0) {
            try {
                CalendarObject calendarObject = calendarArrayList.get(selectedPosition);
                date = calendarObject.isFree() ? ConvertDate.convertDateToString(calendarObject.getDate()) : null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public Date getSelectedDate() {
        Date date = null;
        if (selectedPosition >= 0) {
            try {
                CalendarObject calendarObject = calendarArrayList.get(selectedPosition);
                date = calendarObject.isFree() ? calendarObject.getDate() : null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }
}
