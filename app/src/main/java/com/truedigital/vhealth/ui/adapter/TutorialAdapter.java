package com.truedigital.vhealth.ui.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.truedigital.vhealth.R;

import java.util.ArrayList;

/**
 * Created by nilecon on 6/29/16 AD.
 */
public class TutorialAdapter extends PagerAdapter {

    private Activity activity;
    private ArrayList<Integer> imageList = new ArrayList<>();

    public TutorialAdapter(Activity activity, ArrayList<Integer> imageList) {
        super();
        this.activity = activity;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_tutorial, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageviewTutorial);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(imageList.get(position));
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setAdjustViewBounds(true);
//        container.addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}