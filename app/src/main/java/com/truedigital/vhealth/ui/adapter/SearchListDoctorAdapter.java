package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ApiDoctorObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class SearchListDoctorAdapter extends RecyclerView.Adapter<SearchListDoctorAdapter.ViewHolder> {

    private ArrayList<ApiDoctorObject.AccountObject> listData = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public SearchListDoctorAdapter(Context context) {
        this.context = context;
    }

    public SearchListDoctorAdapter(Context context, ArrayList<ApiDoctorObject.AccountObject> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_doctor, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ApiDoctorObject.AccountObject accountObject = listData.get(position);
        String specialty = "";
        String subSpecialty = "";
        int i = 0;
        for (ApiDoctorObject.AccountObject.Skill skill : accountObject.getSpecialtyList()) {
            if (i == 0) {
                specialty = skill.getDetail();
            } else {
                specialty = specialty + ", " + skill.getDetail();
            }
            i++;
        }

        i = 0;
        for (ApiDoctorObject.AccountObject.Skill skill : accountObject.getSubSpecialtyList()) {
            if (i == 0) {
                subSpecialty = skill.getDetail();
            } else {
                subSpecialty = subSpecialty + ", " + skill.getDetail();
            }
            i++;
        }

        /*
        if (accountObject.isChat()) {
            holder.imgChat.setVisibility(View.VISIBLE);
        } else {
            holder.imgChat.setVisibility(View.GONE);
        }
        if (accountObject.isVoice()) {
            holder.imgVoice.setVisibility(View.VISIBLE);
        } else {
            holder.imgVoice.setVisibility(View.GONE);
        }
        if (accountObject.isVideo()) {
            holder.imgVideo.setVisibility(View.VISIBLE);
        } else {
            holder.imgVideo.setVisibility(View.GONE);
        }
        */

        String fullname = accountObject.getTitleStudy() + accountObject.getTitleName() +
                accountObject.getName() + " " + accountObject.getLastname();
        /*
        holder.txtName.setText(fullname);
        holder.txtSpecialty.setText(specialty);
        holder.txtSubSpecialty.setText(subSpecialty);
        holder.txtReviews.setText(context.getString(R.string.detail_doctor_reviews, accountObject.getViewer()));
        holder.ratingBar.setRating(accountObject.getAmountRate());
        holder.txtPrice.setText(context.getString(R.string.money_baht_B_int_per_minute, accountObject.getContactPrice()));
        holder.imgLanguage.setImageResource(LanguageIcon.getLanguageIcon(accountObject.getLanguageId()));
        holder.imgVideo.setSelected(false);
        holder.imgVoice.setSelected(false);
        holder.imgChat.setSelected(false);
        */

        Glide.with(context)
                .load(accountObject.getProfileImage()).asBitmap()
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .into(holder.imgProfile);

        holder.tvTitle.setText(fullname);
        holder.tvSubTitle.setText(specialty);

        if (accountObject.isFavorite()) {
            holder.ivFavorite.setColorFilter(ContextCompat.getColor(context,R.color.color_red));
        }
        else {
            holder.ivFavorite.setColorFilter(ContextCompat.getColor(context,R.color.color_gray));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });

        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onFavoriteClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final TextView tvTitle;
        private final TextView tvSubTitle;
        private final ImageView ivFavorite;

        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.card_image);
            tvTitle = (TextView) v.findViewById(R.id.card_title);
            tvSubTitle= (TextView) v.findViewById(R.id.card_text);
            ivFavorite = (ImageView) v.findViewById(R.id.favorite_button);
        }
    }

    public ArrayList<ApiDoctorObject.AccountObject> getListData() {
        return listData;
    }

    public void setListData(ArrayList<ApiDoctorObject.AccountObject> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void addListData(List<ApiDoctorObject.AccountObject> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public void setFavorite(int position, boolean isFavorite) {
        this.listData.get(position).setFavorite(isFavorite);
        notifyDataSetChanged();
    }

    public boolean isFavorite(int position) {
        return this.listData.get(position).isFavorite();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onFavoriteClick(View view, int position);
    }

    public void setOnClickListener(SearchListDoctorAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
