package com.truedigital.vhealth.ui.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.truedigital.vhealth.R;

public class ArticleBottomSheetFragment extends BottomSheetDialogFragment {

    private OnItemSelectListerner onItemSelectListerner;

    public static ArticleBottomSheetFragment newInstance() {

        Bundle args = new Bundle();

        ArticleBottomSheetFragment fragment = new ArticleBottomSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setOnItemSelectListerner(OnItemSelectListerner onItemSelectListerner) {
        this.onItemSelectListerner = onItemSelectListerner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.bottomsheet_article, container, false);
        View view = inflater.inflate(R.layout.bottomsheet_article, container, false);

        LinearLayout layoutWrite = (LinearLayout) view.findViewById(R.id.layout_write);
        LinearLayout layoutUpload = (LinearLayout) view.findViewById(R.id.layout_upload);

        layoutWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListerner != null) {
                    onItemSelectListerner.onClickWrite();
                }
                dismiss();
            }
        });

        layoutUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListerner != null) {
                    onItemSelectListerner.onClickUpload();
                }
                dismiss();
            }
        });

        return view;
    }

    public interface OnItemSelectListerner {
        void onClickWrite();
        void onClickUpload();
    }
}
