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
import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.GlideConfig;

import java.util.ArrayList;

public class ListHealthInformationMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private enum ItemType {
        MenuItem,
        OtherItem
    }

    private ArrayList<EhrMenuObject> dataArrayList;

    private Context context;
    private ListHealthInformationMenuAdapter.OnItemClickListener onItemClickListener;

    public ListHealthInformationMenuAdapter(Context context) {
        this.context = context;
    }

    public ListHealthInformationMenuAdapter(Context context, ArrayList<EhrMenuObject> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    public void setListData(ArrayList<EhrMenuObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        EhrMenuObject item = dataArrayList.get(position);
        if (!item.getMenuCode().equals(AppConstants.EHR_MENU_EMPTY)) {
            return ListHealthInformationMenuAdapter.ItemType.MenuItem.ordinal();
        } else {
            return ListHealthInformationMenuAdapter.ItemType.OtherItem.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListHealthInformationMenuAdapter.ItemType.MenuItem.ordinal()) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_health_information_menu, parent, false);
            return new ListHealthInformationMenuAdapter.MenuViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_health_information_other, parent, false);
            return new ListHealthInformationMenuAdapter.OtherViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        EhrMenuObject data = dataArrayList.get(position);
        if (holder.getItemViewType() == ListHealthInformationMenuAdapter.ItemType.MenuItem.ordinal()) {
            this.initLayoutMenu((ListHealthInformationMenuAdapter.MenuViewHolder) holder, data);
        } else {
            this.initLayoutOther((ListHealthInformationMenuAdapter.OtherViewHolder) holder, data);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EhrMenuObject data = dataArrayList.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    private void initLayoutMenu(ListHealthInformationMenuAdapter.MenuViewHolder holder, EhrMenuObject data) {
        holder.txtMenuName.setText(data.getDisplayName());
        Glide.with(context)
                .load(GlideConfig.getGlideHeader(data.getImageUrl())).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(holder.imgMenu);
    }

    private void initLayoutOther(ListHealthInformationMenuAdapter.OtherViewHolder holder, EhrMenuObject data) {
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(EhrMenuObject data);
    }

    public void setOnClickListener(ListHealthInformationMenuAdapter.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView txtMenuName;
        ImageView imgMenu;

        public MenuViewHolder(View v) {
            super(v);
            txtMenuName = (TextView) v.findViewById(R.id.txtMenuName);
            imgMenu = (ImageView) v.findViewById(R.id.imgMenu);
        }
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {

        public OtherViewHolder(View v) {
            super(v);
        }
    }
}
