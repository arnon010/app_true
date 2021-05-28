package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.RecommendMedicationObject;

import java.util.ArrayList;
import java.util.List;

public class ListRecommendMedicationAdapter extends RecyclerView.Adapter<ListRecommendMedicationAdapter.ViewHolder> {

    private Context context;
    private List<RecommendMedicationObject> listData = new ArrayList<>();
    private ListRecommendMedicationAdapter.OnConfirmClickListener onConfirmClickListener;

    public ListRecommendMedicationAdapter(Context context) {
        this.context = context;
    }

    public void setListData(List<RecommendMedicationObject> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public ListRecommendMedicationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recommend_medication, parent, false);
        ListRecommendMedicationAdapter.ViewHolder vh = new ListRecommendMedicationAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListRecommendMedicationAdapter.ViewHolder holder, final int position) {
        RecommendMedicationObject data = listData.get(position);
        holder.txtDrugName.setText(data.getTitle());
        holder.txtUnit.setText(data.getUnit());
        holder.txtHowTo.setText(data.getShortDescription());


        if (position == getItemCount() - 1) {
            holder.txtDrugName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 6f));
            holder.txtUnit.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 6f));
            holder.txtHowTo.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 6f));
            holder.btnBuyMedication.setVisibility(View.VISIBLE);
            holder.btnBuyMedication.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onConfirmClickListener.onConfirmClick();
                }
            });
        } else {
            holder.txtDrugName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 7f));
            holder.txtUnit.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 7f));
            holder.txtHowTo.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 7f));
            holder.btnBuyMedication.setVisibility(View.INVISIBLE);
        }
    }

    public interface OnConfirmClickListener {
        void onConfirmClick();
    }

    public void setOnClickListener(ListRecommendMedicationAdapter.OnConfirmClickListener OnConfirmClickListener) {
        this.onConfirmClickListener = OnConfirmClickListener;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDrugName;
        private TextView txtUnit;
        private TextView txtHowTo;
        private ImageView btnBuyMedication;

        public ViewHolder(View v) {
            super(v);
            txtDrugName = (TextView) v.findViewById(R.id.txtDrugName);
            txtUnit = (TextView) v.findViewById(R.id.txtUnit);
            txtHowTo = (TextView) v.findViewById(R.id.txtHowTo);
            btnBuyMedication = (ImageView) v.findViewById(R.id.btnBuyMedication);

        }
    }
}
