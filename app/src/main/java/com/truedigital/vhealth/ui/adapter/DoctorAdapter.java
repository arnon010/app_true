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
import com.truedigital.vhealth.model.ItemDoctorContactTypeDao;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.utils.LanguageSkillsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemDoctorDao> listData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public DoctorAdapter(Context context) {
        this.context = context;
    }

    public DoctorAdapter(Context context, List<ItemDoctorDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_detail, parent, false);
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
        String msg_nexttime = context.getString(R.string.list_doctor_next_timeslot,data.getClosestAvailableInMinute());
        holder.tvNextTimeSlot.setText(msg_nexttime);
        holder.tvSubTitle.setText(data.getPricePerMinuteFormat());


        //..display icon channel
        holder.ivChat.setVisibility(View.GONE);
        holder.ivVoice.setVisibility(View.GONE);
        holder.ivVideo.setVisibility(View.GONE);
        if (data.getContactTypes() != null) {
            List<ItemDoctorContactTypeDao> listContactType = data.getContactTypes();
            for (ItemDoctorContactTypeDao conTactType : listContactType) {
                if (conTactType.getTypeCode().equals(ConsultDao.CHANNEL_CHAT))
                    holder.ivChat.setVisibility(View.VISIBLE);
                if (conTactType.getTypeCode().equals(ConsultDao.CHANNEL_VOICE))
                    holder.ivVoice.setVisibility(View.VISIBLE);
                if (conTactType.getTypeCode().equals(ConsultDao.CHANNEL_VIDEO))
                    holder.ivVideo.setVisibility(View.VISIBLE);
            }
        }

        //..display language skill
        new LanguageSkillsUtil(data.getLanguageSkills()).show(holder.ivLangTh, holder.ivLangEn);

        //..display favorite
        //boolean isFavorite;
        int resIdFavorite = R.drawable.ic_favorite_white;
        if (data.getDoctorLangInformations() != null) {
            //isFavorite = data.getDoctorLangInformations().get(0).isFavorite();
            resIdFavorite = R.drawable.ic_favorite_green;
        }
        Glide.with(context)
                .load(resIdFavorite).asBitmap()
                .error(R.drawable.ic_favorite_white)
                .placeholder(R.drawable.ic_favorite_white)
                .into(holder.imgFavorite);
        holder.imgFavorite.setVisibility(View.VISIBLE);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });



        /*
        if (data.isFavorite()) {
            holder.ivFavorite.setColorFilter(ContextCompat.getColor(context,R.color.color_red));
        }
        else {
            holder.ivFavorite.setColorFilter(ContextCompat.getColor(context,R.color.color_gray));
        }
        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onFavoriteClick(view, position);
            }
        });
        */

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
        return 1;
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
        }
    }

    public List<ItemDoctorDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemDoctorDao> listData) {
        this.listData = listData;
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
