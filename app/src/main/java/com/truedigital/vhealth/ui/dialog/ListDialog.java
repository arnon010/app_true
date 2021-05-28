package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;

public class ListDialog extends Dialog {

    private Context context;
    private RecyclerView rvData;
    private RecyclerView.Adapter adapter;

    public ListDialog(@NonNull Context context, RecyclerView.Adapter adapter) {
        super(context);
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        final View view = getLayoutInflater().inflate(R.layout.layout_list, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setupView(view);

        this.show();
    }

    private void setupView(View view){
        rvData = (RecyclerView) view.findViewById(R.id.recycler_data_list);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(gridLayoutManager);
        rvData.addItemDecoration(new ListDividerItemDecoration(context));
        rvData.setAdapter(adapter);
    }



}
