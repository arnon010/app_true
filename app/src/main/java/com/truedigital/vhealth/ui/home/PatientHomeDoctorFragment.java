package com.truedigital.vhealth.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.adapter.SearchListDoctorAdapter;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class PatientHomeDoctorFragment extends Fragment {

    private ViewPager viewPager;

    public PatientHomeDoctorFragment() {
        super();
    }

    public static PatientHomeDoctorFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("ARGS_POSITION",position);
        PatientHomeDoctorFragment fragment = new PatientHomeDoctorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        SearchListDoctorAdapter adapter = new SearchListDoctorAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadDoctor();
        return recyclerView;

    }

    private void loadDoctor() {

    }

    private void bindView(View rootView) {
        //viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
    }

    private void initInstances(View rootView) {
        setRetainInstance(true);
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
