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
import com.truedigital.vhealth.model.BankAccountObject;
import com.truedigital.vhealth.utils.GlideConfig;

import java.util.ArrayList;
import java.util.List;

public class ListBankAdapter extends RecyclerView.Adapter<ListBankAdapter.ViewHolder> {

    private Context context;
    private List<BankAccountObject> listData = new ArrayList<>();

    public ListBankAdapter(Context context) {
        this.context = context;
    }

    public ListBankAdapter(Context context, List<BankAccountObject> listData) {
        this.context = context;
        this.listData = listData;
    }

    public void setListData(List<BankAccountObject> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public ListBankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_info, parent, false);
        ListBankAdapter.ViewHolder vh = new ListBankAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListBankAdapter.ViewHolder holder, final int position) {
        BankAccountObject data = listData.get(position);
        Glide.with(context)
                .load(GlideConfig.getGlideHeader(data.getIcon())).asBitmap()
                .error(R.drawable.ic_action_app)
                .placeholder(R.drawable.ic_action_app)
                .into(holder.imgIcBank);

        holder.txtTitle.setText(data.getBankName());
        holder.txtAccount.setText(data.getAccountNumber());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcBank;
        private TextView txtTitle;
        private TextView txtAccount;

        public ViewHolder(View v) {
            super(v);
            imgIcBank = (ImageView) v.findViewById(R.id.imgIcBank);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtAccount = (TextView) v.findViewById(R.id.txtAccount);
        }
    }
}
