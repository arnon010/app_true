package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;


/**
 * Created by warinthorn_s on 5/22/2018.
 */

public class ListBottomSheetFragment extends BottomSheetDialogFragment {

    private int layout;
    private int height;

    private RecyclerView rvData;
    private RecyclerView.Adapter adapter;

    public static ListBottomSheetFragment newInstance(int layout, int height, RecyclerView.Adapter adapter) {
        ListBottomSheetFragment fragment = new ListBottomSheetFragment();
        fragment.layout = layout;
        fragment.height = height;
        fragment.adapter = adapter;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View inflatedView = View.inflate(getContext(), this.layout, null);
        this.setupView(inflatedView);

        dialog.setContentView(inflatedView);

        View parent = (View) inflatedView.getParent();
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        bottomSheetBehavior.setPeekHeight(this.height);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        params.height = this.height;
        parent.setLayoutParams(params);

    }

    private void setupView(View view){
        rvData = (RecyclerView) view.findViewById(R.id.recycler_data_list);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(gridLayoutManager);
        rvData.addItemDecoration(new ListDividerItemDecoration(getActivity()));
        rvData.setAdapter(adapter);
    }


}
