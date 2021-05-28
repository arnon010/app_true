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
import com.truedigital.vhealth.model.LaboratoryOtherObject;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.GlideConfig;
import com.truedigital.vhealth.utils.SwipeRevealLayout;

import java.util.ArrayList;
import java.util.List;

public class ListLaboratoryOtherAdapter extends RecyclerView.Adapter<ListLaboratoryOtherAdapter.ViewHolder> {

    private Context context;
    private List<LaboratoryOtherObject> listData = new ArrayList<>();
    private ListLaboratoryOtherAdapter.OnItemClickListener onItemClickListener;
    private ListLaboratoryOtherAdapter.OnDeleteClickListener onDeleteClickListener;

    public ListLaboratoryOtherAdapter(Context context) {
        this.context = context;
    }

    public void setListData(List<LaboratoryOtherObject> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public List<LaboratoryOtherObject> getListData() {
        return this.listData;
    }

    @Override
    public ListLaboratoryOtherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_laboratory_other, parent, false);
        ListLaboratoryOtherAdapter.ViewHolder vh = new ListLaboratoryOtherAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ListLaboratoryOtherAdapter.ViewHolder holder, final int position) {
        LaboratoryOtherObject data = listData.get(position);

        ((SwipeRevealLayout) holder.itemView).close(false);
        ((SwipeRevealLayout) holder.itemView).setLockDrag(false);

        Glide.with(context)
                .load(GlideConfig.getGlideHeader(data.getImageUrl())).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.imgLab);

        holder.txtTitle.setText(data.getTitle());
        holder.txtFromName.setText(data.getPlace());

        if (data.getLabDate() != null) {
            holder.linImageDescription.setVisibility(View.VISIBLE);
            holder.txtDate.setText(ConvertDate.convertDateNewLineYear(data.getLabDate()));
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
                    LaboratoryOtherObject data = listData.get(position);
                    onItemClickListener.onItemClick(data);
                }

            }
        });


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SwipeRevealLayout) holder.itemView).setLockDrag(false);
                LaboratoryOtherObject data = listData.get(position);
                onDeleteClickListener.onDeleteClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(LaboratoryOtherObject data);
    }

    public void setOnClickListener(ListLaboratoryOtherAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(LaboratoryOtherObject data);
    }

    public void setOnDeleteClickListener(ListLaboratoryOtherAdapter.OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView mainView;
        private FrameLayout btnDelete;

        private ImageView imgLab;
        private TextView txtTitle;
        private TextView txtFromName;

        private LinearLayout linImageDescription;
        private TextView txtDate;


        public ViewHolder(View v) {
            super(v);
            mainView = (CardView) v.findViewById(R.id.card_view);
            btnDelete = (FrameLayout) v.findViewById(R.id.btnDelete);

            imgLab = (ImageView) v.findViewById(R.id.card_image);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtFromName = (TextView) v.findViewById(R.id.txtFromName);

            linImageDescription = (LinearLayout) v.findViewById(R.id.linImageDescription);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
        }
    }
}
