package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.decoration.ListEhrMenuAdapterDecoration;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.StringUtils;


public class ListGridLayoutDialog extends Dialog {

    private Context context;
    private String title;
    private String titleDescription;

    private TextView txtTitle;
    private TextView txtTitleDescription;
    private RecyclerView rvData;
    private RecyclerView.Adapter adapter;

    public ListGridLayoutDialog(@NonNull Context context, String title, String titleDescription, RecyclerView.Adapter adapter) {
        super(context);
        this.context = context;
        this.title = title;
        this.titleDescription = titleDescription;
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_list_gridlayout, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setupView(view);

        this.show();
    }

    private void setupView(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtTitleDescription = (TextView) view.findViewById(R.id.txtTitleDescription);
        rvData = (RecyclerView) view.findViewById(R.id.recycler_data_list);

        txtTitle.setText(this.title);
        if(this.titleDescription != "" && this.titleDescription != null)
        {
            txtTitleDescription.setText(this.titleDescription);
            txtTitleDescription.setVisibility(View.VISIBLE);
            StringUtils.setUnderline(txtTitleDescription);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(gridLayoutManager);
        rvData.addItemDecoration(new ListEhrMenuAdapterDecoration(context, 2, (int)CommonUtils.convertDpToPixel(context,3), R.drawable.line_divider_white));
        rvData.setAdapter(adapter);
    }

}
