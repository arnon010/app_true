package com.truedigital.vhealth.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.truedigital.vhealth.R;

public class PatientHomeNewsListFragment extends Fragment {

    private ViewPager viewPager;
    private TextView tvName;
    private TextView tvDescription;
    private View coverView;
    private int position;

    public PatientHomeNewsListFragment() {
        super();
    }

    public static PatientHomeNewsListFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("ARGS_POSITION",position);
        PatientHomeNewsListFragment fragment = new PatientHomeNewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.patient_home_news_list, container, false);
        View rootView = inflater.inflate(R.layout.item_article, container, false);
        bindView(rootView);
        initInstances(rootView);
        return rootView;
    }

    private void bindView(View rootView) {
        tvName = (TextView) rootView.findViewById(R.id.tvName);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        coverView = (View) rootView.findViewById(R.id.overlayView);
    }

    private void initInstances(View rootView) {
        setRetainInstance(true);
        position = getArguments().getInt("ARGS_POSITION",0);
        tvName.setText("Hello "+ position);
        tvDescription.setText("Description");
        coverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Not implement article",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
