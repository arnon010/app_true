package com.truedigital.vhealth.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.CountryDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 12/24/2017 AD.
 */

public class CountryAdapter extends BaseAdapter {

    private Context mContext;
    private List<CountryDao> listData = new ArrayList<CountryDao>();

    public CountryAdapter(Context mContext, List<CountryDao> listData) {
        this.mContext = mContext;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public CountryDao getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listData.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.list_item_country, null);

        ImageView ivImage = (ImageView) view.findViewById(R.id.imageView);
        TextView tvName = (TextView) view.findViewById(R.id.textView);

        ivImage.setImageResource(listData.get(i).getResIdFlag());
        tvName.setText(listData.get(i).getName());
        return view;
    }

}
