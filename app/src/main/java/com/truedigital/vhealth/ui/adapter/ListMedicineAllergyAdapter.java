package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.MedicineAllergyObject;
import com.truedigital.vhealth.utils.GlideConfig;
import com.truedigital.vhealth.utils.SwipeRevealLayout;

import java.util.ArrayList;
import java.util.List;

public class ListMedicineAllergyAdapter extends RecyclerView.Adapter<ListMedicineAllergyAdapter.ViewHolder> {

    private Context context;
    private List<MedicineAllergyObject> listData = new ArrayList<>();
    private ListMedicineAllergyAdapter.OnItemClickListener onItemClickListener;
    private ListMedicineAllergyAdapter.OnDeleteClickListener onDeleteClickListener;

    public ListMedicineAllergyAdapter(Context context) {
        this.context = context;
    }

    public void setListData(List<MedicineAllergyObject> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public List<MedicineAllergyObject> getListData() {
        return this.listData;
    }

    @Override
    public ListMedicineAllergyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medicine_allergy, parent, false);
        ListMedicineAllergyAdapter.ViewHolder vh = new ListMedicineAllergyAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ListMedicineAllergyAdapter.ViewHolder holder, final int position) {
        MedicineAllergyObject data = listData.get(position);

        ((SwipeRevealLayout) holder.itemView).close(false);
        ((SwipeRevealLayout) holder.itemView).setLockDrag(false);

        Glide.with(context)
                .load(GlideConfig.getGlideHeader(data.getImageUrl())).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.img);

        holder.txtTitle.setText(data.getTitle());

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
                    MedicineAllergyObject data = listData.get(position);
                    onItemClickListener.onItemClick(data);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SwipeRevealLayout) holder.itemView).setLockDrag(false);
                MedicineAllergyObject data = listData.get(position);
                onDeleteClickListener.onDeleteClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(MedicineAllergyObject data);
    }

    public void setOnClickListener(ListMedicineAllergyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(MedicineAllergyObject data);
    }

    public void setOnDeleteClickListener(ListMedicineAllergyAdapter.OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView mainView;
        private FrameLayout btnDelete;

        private ImageView img;
        private TextView txtTitle;


        public ViewHolder(View v) {
            super(v);
            mainView = (CardView) v.findViewById(R.id.card_view);
            btnDelete = (FrameLayout) v.findViewById(R.id.btnDelete);

            img = (ImageView) v.findViewById(R.id.card_image);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
        }
    }
}