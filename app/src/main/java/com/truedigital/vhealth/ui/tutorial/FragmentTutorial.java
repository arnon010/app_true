package com.truedigital.vhealth.ui.tutorial;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;


/**
 * Created by songkrit on 11/16/2014.
 */
public class FragmentTutorial extends Fragment {

    private int position;
    private ImageView imageView;
    private TextView tv_title;

    public FragmentTutorial() {
        super();
    }

    public static FragmentTutorial newInstance(int position) {
        FragmentTutorial fragment = new FragmentTutorial();
        Bundle args = new Bundle();
        args.putInt("position",position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        position = getArguments().getInt("position");
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        tv_title = (TextView) rootView.findViewById(R.id.tv_title);

        String[] strIntro = getResources().getStringArray(R.array.intro);
        tv_title.setText(strIntro[position]);
        //..
        switch(position) {
            //case 0: imageView.setImageResource(R.drawable.tutorial_01); break;
            //case 1: imageView.setImageResource(R.drawable.tutorial_02); break;
            //case 2: imageView.setImageResource(R.drawable.tutorial_03); break;
            case 0: imageView.setImageResource(R.drawable.intro_1);break;
            case 1: imageView.setImageResource(R.drawable.intro_2); break;
            case 2: imageView.setImageResource(R.drawable.intro_3); break;
        }

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
