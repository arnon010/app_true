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
import com.truedigital.vhealth.model.DoctorNoteObject;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.GlideConfig;

import java.util.ArrayList;
import java.util.List;

public class ListDoctorNoteAdapter extends RecyclerView.Adapter<ListDoctorNoteAdapter.ViewHolder> {

    private Context context;
    private List<DoctorNoteObject> listData = new ArrayList<>();
    private ListDoctorNoteAdapter.OnItemClickListener onItemClickListener;

    public ListDoctorNoteAdapter(Context context) {
        this.context = context;
    }

    public void setListData(List<DoctorNoteObject> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public List<DoctorNoteObject> getListData() {
       return  this.listData ;
    }

    @Override
    public ListDoctorNoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_doctor_note, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListDoctorNoteAdapter.ViewHolder holder, final int position) {
        DoctorNoteObject data = listData.get(position);

        Glide.with(context)
                .load(GlideConfig.getGlideHeader(data.getDoctorImage())).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.imgProfile);

        holder.txtDoctorName.setText(data.getDoctorName());
        holder.txtSubCategoryName.setText(data.getCategoryName());

        holder.txtAppointmentTime.setText(ConvertDate.StringDateServiceFormat(data.getAppointmentTimeString()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorNoteObject data = listData.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(DoctorNoteObject data);
    }

    public void setOnClickListener(ListDoctorNoteAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProfile;
        private TextView txtDoctorName;
        private TextView txtSubCategoryName;
        private TextView txtAppointmentTime;

        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.card_image);
            txtDoctorName = (TextView) v.findViewById(R.id.txtDoctorName);
            txtSubCategoryName= (TextView) v.findViewById(R.id.txtSubCategoryName);
            txtAppointmentTime = (TextView) v.findViewById(R.id.txtAppointmentTime);
        }
    }
}
