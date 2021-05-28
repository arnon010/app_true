package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.PatientRelationshipObject;

import java.util.ArrayList;

public class ListFamilyMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private enum ItemType {
        MenuItem,
        OtherItem
    }

    private ArrayList<PatientRelationshipObject> dataArrayList;

    private Context context;
    private ListFamilyMenuAdapter.OnItemClickListener onItemClickListener;

    public ListFamilyMenuAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<PatientRelationshipObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        PatientRelationshipObject item = dataArrayList.get(position);
        if (item.getRelationshipId() != 0) {
            return ListFamilyMenuAdapter.ItemType.MenuItem.ordinal();
        } else {
            return ListFamilyMenuAdapter.ItemType.OtherItem.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListFamilyMenuAdapter.ItemType.MenuItem.ordinal()) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_family_menu, parent, false);
            return new ListFamilyMenuAdapter.MenuViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_family_other, parent, false);
            return new ListFamilyMenuAdapter.OtherViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        PatientRelationshipObject data = dataArrayList.get(position);
        if(holder.getItemViewType() == ListFamilyMenuAdapter.ItemType.MenuItem.ordinal())
        {
            this.initLayoutMenu((ListFamilyMenuAdapter.MenuViewHolder)holder, data);
        }
        else {
            this.initLayoutOther((ListFamilyMenuAdapter.OtherViewHolder)holder, data);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientRelationshipObject data = dataArrayList.get(position);
                onItemClickListener.onItemClick(data);
            }
        });
    }

    private void initLayoutMenu(ListFamilyMenuAdapter.MenuViewHolder holder, PatientRelationshipObject data) {
        holder.txtMenuName.setText(data.getRelationshipName());
    }

    private void initLayoutOther(ListFamilyMenuAdapter.OtherViewHolder holder, PatientRelationshipObject data) {

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(PatientRelationshipObject data);
    }

    public void setOnClickListener(ListFamilyMenuAdapter.OnItemClickListener OnItemClickListener) {
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
