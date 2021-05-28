package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.PregnantHistoryObject;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.GlideConfig;
import com.truedigital.vhealth.utils.SwipeRevealLayout;

import java.util.ArrayList;
import java.util.List;

public class ListPregnantHistoryAdapter extends RecyclerView.Adapter<ListPregnantHistoryAdapter.ViewHolder> {

    private Context context;
    private List<PregnantHistoryObject> listData = new ArrayList<>();
    private ListPregnantHistoryAdapter.OnItemClickListener onItemClickListener;
    private ListPregnantHistoryAdapter.OnDeleteClickListener onDeleteClickListener;

    public ListPregnantHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setListData(List<PregnantHistoryObject> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public List<PregnantHistoryObject> getListData() {
        return this.listData;
    }

    @Override
    public ListPregnantHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pregnant_history, parent, false);
        ListPregnantHistoryAdapter.ViewHolder vh = new ListPregnantHistoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ListPregnantHistoryAdapter.ViewHolder holder, final int position) {
        PregnantHistoryObject data = listData.get(position);

        ((SwipeRevealLayout) holder.itemView).close(false);
        ((SwipeRevealLayout) holder.itemView).setLockDrag(false);

        Glide.with(context)
                .load(GlideConfig.getGlideHeader(data.getImageUrl())).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.imgPregnant);

        holder.txtTitle.setText(data.getTitle());
        holder.txtPlace.setText(data.getPlace());

        if (data.getRecordDate() != null) {
            holder.linImageDescription.setVisibility(View.VISIBLE);
            holder.txtDate.setText(ConvertDate.convertDateNewLineYear(data.getRecordDate()));
        } else {
            holder.linImageDescription.setVisibility(View.INVISIBLE);
        }

        ((SwipeRevealLayout) holder.itemView).setOnOpenListener(new SwipeRevealLayout.OnOpenListener() {
            @Override
            public void onOpen() {
                ((SwipeRevealLayout) holder.itemView).setLockDrag(true);
            }
        });

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((SwipeRevealLayout) holder.itemView).isOpen()) {
                    ((SwipeRevealLayout) holder.itemView).close(true);
                    ((SwipeRevealLayout) holder.itemView).setLockDrag(false);
                } else {
                    PregnantHistoryObject data = listData.get(position);
                    onItemClickListener.onItemClick(data);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SwipeRevealLayout) holder.itemView).setLockDrag(false);
                PregnantHistoryObject data = listData.get(position);
                onDeleteClickListener.onDeleteClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(PregnantHistoryObject data);
    }

    public void setOnClickListener(ListPregnantHistoryAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(PregnantHistoryObject data);
    }

    public void setOnDeleteClickListener(ListPregnantHistoryAdapter.OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView mainView;
        private FrameLayout btnDelete;

        private ImageView imgPregnant;
        private TextView txtTitle;
        private TextView txtPlace;

        private LinearLayout linImageDescription;
        private TextView txtDate;


        public ViewHolder(View v) {
            super(v);
            mainView = (CardView) v.findViewById(R.id.card_view);
            btnDelete = (FrameLayout) v.findViewById(R.id.btnDelete);

            imgPregnant = (ImageView) v.findViewById(R.id.card_image);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtPlace = (TextView) v.findViewById(R.id.txtPlace);

            linImageDescription = (LinearLayout) v.findViewById(R.id.linImageDescription);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
        }
    }
}
