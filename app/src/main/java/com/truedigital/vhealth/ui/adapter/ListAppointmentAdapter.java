package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseViewHolder;
import com.truedigital.vhealth.utils.LanguageSkillsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 10/25/2016 AD.
 */

public class ListAppointmentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private List<ItemAppointmentDao> listData = new ArrayList<>();
    private ItemAppointmentDao data;
    private OnItemClickListener onItemClickListener;
    private OnRetryClickListener onRetryClickListener;

    int position;
    ArrayList<Integer> listImage = new ArrayList<>();



    public ListAppointmentAdapter(Context context) {
        this.context = context;
        //setTempImage();
    }

    public ListAppointmentAdapter(Context context, List<ItemAppointmentDao> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_appointment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

        */
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_appointment, parent, false));

            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_view, parent, false));

        }


    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    private void setTempImage() {
        listImage.add(R.drawable.ic_category_baby);
        listImage.add(R.drawable.ic_category_women);
        listImage.add(R.drawable.ic_category_skin);
        listImage.add(R.drawable.ic_category_bone);
        listImage.add(R.drawable.ic_category_age);
        listImage.add(R.drawable.ic_category_baby);
        listImage.add(R.drawable.ic_category_checkup);
        listImage.add(R.drawable.ic_category_medicine);
        listImage.add(R.drawable.ic_category_bone);
        listImage.add(R.drawable.ic_category_checkup);
        listImage.add(R.drawable.ic_category_medicine);
        listImage.add(R.drawable.ic_category_bone);
    }

    private int getImage(int position) {
        return listImage.get(position);
    }


    /*
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        ItemAppointmentDao data = listData.get(position);

        Glide.with(context)
                .load(data.getDoctorProfileImage()).asBitmap()
                .error(R.drawable.ic_profile_user)
                .placeholder(R.drawable.ic_profile_user)
                .into(holder.imgProfile);

        holder.imgChannel.setImageResource(data.getShowChanelIcon());
        holder.imgChannelUser.setImageResource(data.getShowChanelIcon());

        holder.tvTitle.setText(data.getDoctorName());

        holder.tvCategory.setText(data.getCategoryFullName());
        holder.tvTime.setText(data.getShowFullDate());
        holder.tvContact_minute.setText(data.getContactMinute() + " " + context.getString(R.string.time_minute));

        holder.tvLangTh.setVisibility(View.GONE);
        holder.tvLangEn.setVisibility(View.GONE);
        if (!AppManager.getDataManager().isDoctor()) {
            for (String languageSkills : data.getLanguageSkills()) {
                if (languageSkills.equals("TH")) {
                    holder.tvLangTh.setVisibility(View.VISIBLE);
                }
                if (languageSkills.equals("EN")) {
                    holder.tvLangEn.setVisibility(View.VISIBLE);
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position, holder.imgProfile);
            }
        });

        ViewCompat.setTransitionName(holder.imgProfile,"transition" + position);

    }
    */

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

    public class ViewHolder extends BaseViewHolder {

        private final ImageView imgProfile;
        private final ImageView imgChannel;
        private final TextView tvTitle;
        private final TextView tvCategory;
        private final TextView tvSubTitle;
        private final ImageView ivFavorite;
        private final TextView tvTime;
        private final TextView tvContact_minute;

        private final ImageView imgChannelUser;
        private final ImageView ivLangTh;
        private final ImageView ivLangEn;


        public ViewHolder(View v) {
            super(v);
            imgProfile = (ImageView) v.findViewById(R.id.card_image);
            imgChannel = (ImageView) v.findViewById(R.id.card_image_icon);
            tvTitle = (TextView) v.findViewById(R.id.card_title);
            tvCategory = (TextView) v.findViewById(R.id.card_doctore_category);
            tvSubTitle= (TextView) v.findViewById(R.id.card_text);
            ivFavorite = (ImageView) v.findViewById(R.id.favorite_button);
            tvTime = (TextView) v.findViewById(R.id.card_time);
            tvContact_minute = (TextView) v.findViewById(R.id.card_contact_minute);

            imgChannelUser = (ImageView) v.findViewById(R.id.img_channel_user);
            ivLangTh = (ImageView) v.findViewById(R.id.item_lang_th);
            ivLangEn = (ImageView) v.findViewById(R.id.item_lang_en);


            if (AppManager.getDataManager().isDoctor()) {
                imgChannel.setVisibility(View.VISIBLE);
                tvContact_minute.setVisibility(View.VISIBLE);
                tvCategory.setVisibility(View.GONE);
                imgChannelUser.setVisibility(View.GONE);
                ivLangTh.setVisibility(View.GONE);
                ivLangEn.setVisibility(View.GONE);
            }
            else {
                imgChannel.setVisibility(View.GONE);
                tvContact_minute.setVisibility(View.GONE);
                tvCategory.setVisibility(View.VISIBLE);
                imgChannelUser.setVisibility(View.VISIBLE);
                ivLangTh.setVisibility(View.VISIBLE);
                ivLangEn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);
            ItemAppointmentDao data = listData.get(position);

            Glide.with(context)
                    .load(data.getDoctorProfileImage()).asBitmap()
                    .error(R.drawable.ic_profile_user)
                    .placeholder(R.drawable.ic_profile_user)
                    .into(imgProfile);

            imgChannel.setImageResource(data.getShowChanelIcon());
            imgChannelUser.setImageResource(data.getShowChanelIcon());

            tvTitle.setText(data.getDoctorName());

            tvCategory.setText(data.getCategoryFullName());
            tvTime.setText(data.getShowFullDate());
            tvContact_minute.setText(data.getContactMinute() + " " + context.getString(R.string.time_minute));

            //tvLangTh.setVisibility(View.GONE);
            //tvLangEn.setVisibility(View.GONE);
            if (!AppManager.getDataManager().isDoctor()) {
                //..display language skill
                new LanguageSkillsUtil(data.getLanguageSkills(), true).show(ivLangTh, ivLangEn);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        //..Do stuff
                        onItemClickListener.onItemClick(view, position, imgProfile);
                    }
                }
            });

            ViewCompat.setTransitionName(imgProfile,"transition" + position);
        }

        @Override
        protected void clear() {

        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        private final TextView tv_empty_message;
        private final Button btnRetry;

        public EmptyViewHolder(View v) {
            super(v);

            tv_empty_message = (TextView) v.findViewById(R.id.tv_empty_message);
            btnRetry = (Button) v.findViewById(R.id.btn_empty_retry);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            tv_empty_message.setText(R.string.appointment_empty);
            tv_empty_message.setVisibility(View.VISIBLE);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRetryClickListener != null) {
                        //..Do stuff
                    }
                }
            });
        }

        @Override
        protected void clear() {

        }
    }

    public List<ItemAppointmentDao> getListData() {
        return listData;
    }

    public void setListData(List<ItemAppointmentDao> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public ItemAppointmentDao getData(int position) {
        data = listData.get(position);
        return data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ImageView shareView);
        void onFavoriteClick(View view, int position);
    }

    public interface OnRetryClickListener {
        void onRetryClick();
    }

    public void setOnClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    public void setOnRetryListener(OnRetryClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }


}
