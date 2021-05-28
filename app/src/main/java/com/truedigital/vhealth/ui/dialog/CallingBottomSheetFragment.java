package com.truedigital.vhealth.ui.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.main.MainActivity;

/**
 * Created by songkrit on 6/6/2018 AD.
 */

public class CallingBottomSheetFragment extends BottomSheetDialogFragment {

    private CustomInterface callback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_calling_bar, container, false);
        Button btn_calling = (Button)view.findViewById(R.id.btn_calling);

        //callback = this.callback;
        btn_calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openRoom("","");
                //callback.onCall();
                dismiss();
            }
        });
        return view;
    }

    public void setCallback(CustomInterface callback) {
        this.callback = callback;
    }

    public interface CustomInterface {
        public void onCall();
    }
}
