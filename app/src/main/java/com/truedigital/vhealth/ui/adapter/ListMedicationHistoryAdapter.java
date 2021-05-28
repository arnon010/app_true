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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.MedicationHistoryObject;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.GlideConfig;
import com.truedigital.vhealth.utils.SwipeRevealLayout;

import java.util.ArrayList;
import java.util.List;

public class ListMedicationHistoryAdapter extends RecyclerView.Adapter<ListMedicationHistoryAdapter.ViewHolder> {

    private Context context;
    private List<MedicationHistoryObject> listData = new ArrayList<>();
    private ListMedicationHistoryAdapter.OnItemClickListener onItemClickListener;
    private ListMedicationHistoryAdapter.OnDeleteClickListener onDeleteClickListener;

    public ListMedicationHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setListData(List<MedicationHistoryObject> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public List<MedicationHistoryObject> getListData() {
        return this.listData;
    }

    @Override
    public ListMedicationHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medication_history, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ListMedicationHistoryAdapter.ViewHolder holder, final int position) {
        final MedicationHistoryObject data = listData.get(position);

        ((SwipeRevealLayout) holder.itemView).close(false);
        ((SwipeRevealLayout) holder.itemView).setLockDrag(false);

        if (data.getIsChiiwiiProduct()) {
            ((SwipeRevealLayout) holder.itemView).setLockDrag(true);
            holder.relativeMain.setBackgroundResource(R.color.color_blue_lighter);
        } else {
            ((SwipeRevealLayout) holder.itemView).setLockDrag(false);
            holder.relativeMain.setBackgroundResource(R.color.transparent);

            ((SwipeRevealLayout) holder.itemView).setOnOpenListener(new SwipeRevealLayout.OnOpenListener() {
                @Override
                public void onOpen() {
                    ((SwipeRevealLayout) holder.itemView).setLockDrag(true);
                }
            });
        }

        Glide.with(context)
                .load(GlideConfig.getGlideHeader(data.getImageUrl())).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.imgMedication);

        holder.txtTitle.setText(data.getTitle());
        holder.txtFrom.setText(data.getPlace());

        if (data.getRecordDate() != null) {
            holder.linImageDescription.setVisibility(View.VISIBLE);
            holder.txtDate.setText(ConvertDate.convertDateNewLineYear(data.getRecordDate()));
        } else {
            holder.linImageDescription.setVisibility(View.INVISIBLE);
        }

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((SwipeRevealLayout) holder.itemView).isOpen() && !data.getIsChiiwiiProduct()) {
                    ((SwipeRevealLayout) holder.itemView).close(true);
                    ((SwipeRevealLayout) holder.itemView).setLockDrag(false);
                } else {
                    MedicationHistoryObject data = listData.get(position);
                    onItemClickListener.onItemClick(data);
                }

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!data.getIsChiiwiiProduct()) {
                    ((SwipeRevealLayout) holder.itemView).setLockDrag(false);
                }

                MedicationHistoryObject data = listData.get(position);
                onDeleteClickListener.onDeleteClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(MedicationHistoryObject data);
    }

    public void setOnClickListener(ListMedicationHistoryAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(MedicationHistoryObject data);
    }

    public void setOnDeleteClickListener(ListMedicationHistoryAdapter.OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView mainView;
        private FrameLayout btnDelete;

        private RelativeLayout relativeMain;
        private ImageView imgMedication;
        private TextView txtTitle;
        private TextView txtFrom;

        private LinearLayout linImageDescription;
        private TextView txtDate;


        public ViewHolder(View v) {
            super(v);
            mainView = (CardView) v.findViewById(R.id.card_view);
            btnDelete = (FrameLayout) v.findViewById(R.id.btnDelete);

            relativeMain = (RelativeLayout) v.findViewById(R.id.relativeMain);
            imgMedication = (ImageView) v.findViewById(R.id.card_image);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtFrom = (TextView) v.findViewById(R.id.txtFrom);

            linImageDescription = (LinearLayout) v.findViewById(R.id.linImageDescription);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
        }
    }
}
