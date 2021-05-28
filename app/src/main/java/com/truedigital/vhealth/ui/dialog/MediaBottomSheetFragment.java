package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.MediaObject;
import com.truedigital.vhealth.ui.adapter.ListMediaAdapter;
import com.truedigital.vhealth.ui.decoration.ListEhrMenuAdapterDecoration;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;

import java.util.ArrayList;

public class MediaBottomSheetFragment extends BottomSheetDialogFragment {

    private int height;

    private RecyclerView rvData;
    private MediaBottomSheetFragment.OnClickListener onClickListener;

    public interface OnClickListener {
        void onSelect(MediaObject data);
    }

    public void setOnClickListener(MediaBottomSheetFragment.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static MediaBottomSheetFragment newInstance(int height) {
        MediaBottomSheetFragment fragment = new MediaBottomSheetFragment();
        fragment.height = height;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View inflatedView = View.inflate(getContext(), R.layout.fragment_media_bottom_sheet, null);
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

    private void setupView(View view) {
        rvData = (RecyclerView) view.findViewById(R.id.recycler_data_list);

        ArrayList<MediaObject> mList = new ArrayList<>();
        MediaObject m1 = new MediaObject();
        m1.setMediaId(AppConstants.REQUEST_PICK_IMAGE);
        m1.setMediaName(getResources().getString(R.string.photo_gallery));
        MediaObject m2 = new MediaObject();
        m2.setMediaId(AppConstants.REQUEST_CAMERA);
        m2.setMediaName(getResources().getString(R.string.camera));
        mList.add(m1);
        mList.add(m2);

        ListMediaAdapter adapterMedia = new ListMediaAdapter(getActivity());
        adapterMedia.setOnClickListener(new ListMediaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MediaObject data) {
                onClickListener.onSelect(data);
            }
        });
        adapterMedia.setListData(mList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(gridLayoutManager);
        rvData.addItemDecoration(new ListEhrMenuAdapterDecoration(getActivity(), 2, (int) CommonUtils.convertDpToPixel(getActivity(), 3), R.drawable.line_divider_white));
        rvData.setAdapter(adapterMedia);
    }

}
