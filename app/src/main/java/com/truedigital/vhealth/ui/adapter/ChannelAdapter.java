package com.truedigital.vhealth.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ConsultDao;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private ArrayList<ConsultDao> listData = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;
    private boolean isSelect;

    public ChannelAdapter(Context context) {
        this.context = context;
    }

    public ChannelAdapter(Context context, ArrayList<ConsultDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel, parent, false);
        return new ViewHolder(v);
    }

    private String getChannelText(String title) {
        switch (title){
            case "Chat":
                return context.getResources().getString(R.string.consult_chat);
            case "Voice":
                return context.getResources().getString(R.string.consult_voice);
            default:
                return context.getResources().getString(R.string.consult_video);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ConsultDao data = listData.get(position);

        holder.img_channel.setImageDrawable(ContextCompat.getDrawable(context, data.getResImageDrawable()));
        holder.tvTitle.setText(this.getChannelText(data.getTitleName()));
        holder.tvPrice.setText(data.getPricePerMinuteFormat());
        if (data.isChecked()) {
            holder.img_channel.setSelected(true);
            holder.tvTitle.setSelected(true);
            holder.tvPrice.setSelected(true);
        } else {
            holder.img_channel.setSelected(false);
            holder.tvTitle.setSelected(false);
            holder.tvPrice.setSelected(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img_channel;
        private final TextView tvTitle;
        private final TextView tvPrice;

        public ViewHolder(View v) {
            super(v);
            img_channel = (ImageView) v.findViewById(R.id.img_channel);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvPrice = (TextView) v.findViewById(R.id.tv_price);
        }
    }


    public void setListData(ArrayList<ConsultDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public ArrayList<ConsultDao> getListData() {
        return listData;
    }

    public ConsultDao getData(int position) {
        return listData.get(position);
    }

    public void selectedItem(int position) {
        resetChecked();
        listData.get(position).setChecked(!listData.get(position).isChecked());
        notifyDataSetChanged();
    }

    private void updateSelection(int position) {
        resetChecked();
        listData.get(position).setChecked(true);
        notifyDataSetChanged();
    }

    private void resetChecked() {
        for (ConsultDao data : listData) {
            data.setChecked(false);
        }

    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onFavoriteClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
