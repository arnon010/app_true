package com.truedigital.vhealth.ui.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ConsultDao;
import java.util.ArrayList;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ConsultAdapter extends RecyclerView.Adapter<ConsultAdapter.ViewHolder> {

    private ArrayList<ConsultDao> listData = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;
    private boolean isSelect;

    public ConsultAdapter(Context context) {
        this.context = context;
    }

    public ConsultAdapter(Context context, ArrayList<ConsultDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_consult, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ConsultDao data = listData.get(position);

        int icon = data.getResId();
        if (data.isAvailable()) icon = data.getResIdActive();

        Glide.with(context)
                .load(icon).asBitmap()
                .placeholder(R.drawable.ic_profile_user)
                .into(holder.imgProfile);
        holder.tvTitle.setText(data.getTitleName());

        if (data.isChecked()) {
            holder.imgChecked.setVisibility(View.VISIBLE);
        }
        else {
            holder.imgChecked.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //onItemClickListener.onItemClick(view, position);
            if (data.isAvailable()) {
                updateSelection(position);
            }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final ImageView imgChecked;
        private final TextView tvTitle;

        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.card_image);
            tvTitle = (TextView) v.findViewById(R.id.card_title);
            imgChecked = (ImageView) v.findViewById(R.id.card_image_checked);
        }
    }


    public void setListData(ArrayList<ConsultDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public ArrayList<ConsultDao> getListData() {
        return listData;
    }

    private void updateSelection(int position) {
        resetChecked();
        listData.get(position).setChecked(true);
        notifyDataSetChanged();
    }

    private void resetChecked() {
        for(ConsultDao data : listData) {
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
