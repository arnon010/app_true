package com.truedigital.vhealth.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.ui.adapter.ViewPagerAdapter;
import com.truedigital.vhealth.ui.login.LoginActivity;

import java.util.Locale;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;

public class TutorialActivity extends LocalizationActivity {

    private static final String TAG = "OnBoarding";
    private ViewPager viewPager;
    private ImageView zero;
    private ImageView one;
    private ImageView two;
    private ImageView[] indicators;
    private Button btnFinish;
    private int page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        //..
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!AppManager.getDataManager().isSetLocalFirst()) {
            AppManager.getDataManager().setLocalFirst(true);
            onSetLanguage();
        }

        initInstances();
    }


    private void initInstances() {

        //setContentView(R.layout.activity_onborading);
        //..view Pager
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //.. Indicatore
        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        indicators = new ImageView[]{zero, one, two};
        btnFinish = (Button) findViewById(R.id.btn_start);

        setupViewPager(viewPager);
        viewPager.setCurrentItem(page);
        updateIndicators(page);


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinish();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                updateIndicators(page);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void onSetLanguage() {
        String lang = CommonUtils.getLocalLanguage();
        if(lang.contains("th")) {
            setLanguage(AppConstants.LOCAL_LANG_THAI);
        }else{
            setLanguage(AppConstants.LOCAL_LANG_ENG);
        }
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }

        btnFinish.setVisibility(position == 2 ? View.VISIBLE : View.INVISIBLE);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentTutorial.newInstance(0) ,"" );
        adapter.addFragment(FragmentTutorial.newInstance(1) ,"" );
        adapter.addFragment(FragmentTutorial.newInstance(2) ,"" );
        viewPager.setAdapter(adapter);
    }

    private void onFinish() {
        AppManager.getDataManager().setOpenApp(true);
        startLoginActivity();
        finish();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        Log.e(TAG,"Signup");
    }
}
