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
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.utils.ContactTypeUtil;
import com.truedigital.vhealth.utils.LanguageSkillsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListDoctorAdapter extends RecyclerView.Adapter<ListDoctorAdapter.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemDoctorDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ListDoctorAdapter(Context context) {
        this.context = context;
    }

    public ListDoctorAdapter(Context context, List<ItemDoctorDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_doctor, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

        /*
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_doctor, parent, false));

            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));

        }
        */
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ItemDoctorDao data = listData.get(position);

        Glide.with(context)
                .load(data.getProfileImage()).asBitmap()
                .error(R.drawable.ic_profile_user)
                .placeholder(R.drawable.ic_profile_user)
                .into(holder.imgProfile);


        holder.tvTitle.setText(data.getName());
        //String msg_nexttime = context.getString(R.string.list_doctor_next_timeslot,data.getClosestAvailableInMinute());
        //String msg_nexttime = context.getString(R.string.list_doctor_next_available,data.getClosestAvailableFormat());
        holder.tvNextTimeSlot.setText(data.getClosestAvailableFormat());
        holder.tvSubTitle.setText(data.getPricePerMinuteFormat());
        holder.tvSpecialist.setText(data.getSpecialityDescription());

        //..display icon channel
        new ContactTypeUtil(data.getContactTypes()).show(holder.ivChat, holder.ivVoice, holder.ivVideo);

        //..display language skill
        new LanguageSkillsUtil(data.getLanguageSkills()).show(holder.ivLangTh, holder.ivLangEn);

        if (data.isFavorite()) {
            Glide.with(context)
                    .load(R.drawable.ic_favorite_green).asBitmap()
                    .error(R.drawable.ic_profile_user)
                    .placeholder(R.drawable.ic_profile_user)
                    .into(holder.imgFavorite);

            holder.imgFavorite.setVisibility(View.VISIBLE);
        }
        else {
            holder.imgFavorite.setVisibility(View.INVISIBLE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if (listData != null && listData.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfile;
        private final TextView tvTitle;
        private final TextView tvSubTitle;
        private final TextView tvNextTimeSlot;
        private final ImageView ivFavorite;
        private final ImageView imgFavorite;
        private final ImageView ivChat;
        private final ImageView ivVoice;
        private final ImageView ivVideo;
        private final ImageView ivLangTh;
        private final ImageView ivLangEn;
        private final TextView tvSpecialist;

        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.card_image);
            imgFavorite = (ImageView) v.findViewById(R.id.card_image_icon);
            tvTitle = (TextView) v.findViewById(R.id.card_title);
            tvSubTitle= (TextView) v.findViewById(R.id.card_text);
            ivFavorite = (ImageView) v.findViewById(R.id.favorite_button);
            tvNextTimeSlot = (TextView) v.findViewById(R.id.card_next_timeslot);

            ivChat = (ImageView) v.findViewById(R.id.item_chat);
            ivVoice = (ImageView) v.findViewById(R.id.item_voice);
            ivVideo = (ImageView) v.findViewById(R.id.item_video);

            ivLangTh = (ImageView) v.findViewById(R.id.item_lang_th);
            ivLangEn = (ImageView) v.findViewById(R.id.item_lang_en);
            tvSpecialist = (TextView) v.findViewById(R.id.card_specialist);
            tvSpecialist.setVisibility(View.VISIBLE);
        }
    }


    public List<ItemDoctorDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemDoctorDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void addListData(List<ItemDoctorDao> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public ItemDoctorDao getData(int position) {
        return listData.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

}
