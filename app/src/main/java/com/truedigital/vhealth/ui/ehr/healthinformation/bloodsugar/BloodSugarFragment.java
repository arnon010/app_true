package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.model.GraphBloodSugarCriteriaObject;
import com.truedigital.vhealth.model.TodayBloodGlucoseObject;
import com.truedigital.vhealth.ui.adapter.ViewPagerAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.CustomViewPager;
import com.truedigital.vhealth.utils.StringUtils;

public class BloodSugarFragment extends BaseMvpFragment<BloodSugarFragmentInterface.Presenter>
        implements BloodSugarFragmentInterface.View {

    private CustomViewPager viewPager;
    private RadioGroup rg;
    private RadioButton rdoSearchByDay;
    private RadioButton rdoSearchByWeek;
    private RadioButton rdoSearchByMonth;

    private TextView txtBloodGlucose;
    private TextView txtMore;

    private int patientMenuId;
    private int patientId;
    private String menuCode;

    private ViewPagerAdapter viewPagerAdapter;
    private GraphBloodSugarFragment graphDay;
    private GraphBloodSugarFragment graphWeek;
    private GraphBloodSugarFragment graphMonth;

    private GraphBloodSugarCriteriaObject criteriaDay;
    private GraphBloodSugarCriteriaObject criteriaWeek;
    private GraphBloodSugarCriteriaObject criteriaMonth;

    private int defaultTap = 0;

    public BloodSugarFragment() {
        super();
    }

    public static BloodSugarFragment newInstance(int patientId, int patientMenuId, String menuCode) {
        BloodSugarFragment fragment = new BloodSugarFragment();
        fragment.patientId = patientId;
        fragment.patientMenuId = patientMenuId;
        fragment.menuCode = menuCode;

        fragment.criteriaDay = new GraphBloodSugarCriteriaObject();
        fragment.criteriaDay.setIsCanLoad(true);
        fragment.criteriaDay.setPeriod(0);

        fragment.criteriaWeek = new GraphBloodSugarCriteriaObject();
        fragment.criteriaWeek.setIsCanLoad(false);
        fragment.criteriaWeek.setPeriod(0);

        fragment.criteriaMonth = new GraphBloodSugarCriteriaObject();
        fragment.criteriaMonth.setIsCanLoad(false);
        fragment.criteriaMonth.setPeriod(0);

        fragment.setCriteria();

        fragment.graphDay = GraphBloodSugarFragment.newInstance(fragment.criteriaDay);
        fragment.graphWeek = GraphBloodSugarFragment.newInstance(fragment.criteriaWeek);
        fragment.graphMonth = GraphBloodSugarFragment.newInstance(fragment.criteriaMonth);

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BloodSugarFragmentInterface.Presenter createPresenter() {
        return BloodSugarFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_blood_sugar;
    }

    @Override
    public void bindView(View view) {
        viewPager = (CustomViewPager) view.findViewById(R.id.viewPager);
        rg = (RadioGroup) view.findViewById(R.id.radioGroupSearch);
        rdoSearchByDay = (RadioButton) view.findViewById(R.id.rdoSearchByDay);
        rdoSearchByWeek = (RadioButton) view.findViewById(R.id.rdoSearchByWeek);
        rdoSearchByMonth = (RadioButton) view.findViewById(R.id.rdoSearchByMonth);
        txtBloodGlucose = (TextView) view.findViewById(R.id.txtBloodGlucose);
        txtMore = (TextView) view.findViewById(R.id.txtMore);
    }

    @Override
    public void setupInstance() {
        setupViewPager(viewPager);
    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateTag(position);
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        rdoSearchByDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSelectType(0);
            }
        });

        rdoSearchByWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSelectType(1);
            }
        });

        rdoSearchByMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSelectType(2);
            }
        });

        StringUtils.setUnderline(txtMore);
        txtMore.setOnClickListener(this.onViewMore());

        ((MainActivity) getActivity()).setAddOnClickListener(new MainActivity.OnAddClickListener() {
            @Override
            public void onAddClick() {
                BloodSugarObject data = new BloodSugarObject();
                data.setPatientId(patientId);
                data.setPatientMenuId(patientMenuId);
                data.setMenuCode(menuCode);
                ((MainActivity) getActivity()).openNewFormBloodSugar(data);
            }
        });

        updateTag(defaultTap);
        viewPager.setCurrentItem(defaultTap);

    }

    @Override
    public void initialize() {
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.blood_sugar), false,null, true);
        getPresenter().getTodayBloodGlucose(patientId, patientMenuId);
    }

    @Override
    public void restoreView(Bundle savedInstanceState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void setTodayBloodGlucose(TodayBloodGlucoseObject data) {
        txtBloodGlucose.setText(data.getBloodGlucose());
    }

    private void setCriteria() {
        this.criteriaDay.setType(0);
        this.criteriaDay.setPatientId(patientId);
        this.criteriaDay.setPatientMenuId(patientMenuId);

        this.criteriaWeek.setType(1);
        this.criteriaWeek.setPatientId(patientId);
        this.criteriaWeek.setPatientMenuId(patientMenuId);

        this.criteriaMonth.setType(2);
        this.criteriaMonth.setPatientId(patientId);
        this.criteriaMonth.setPatientMenuId(patientMenuId);
    }

    public void setPatientMenuId(int patientMenuId) {
        this.patientMenuId = patientMenuId;
        this.setCriteria();
    }

    private void setupViewPager(CustomViewPager viewPager) {
        viewPager.setSwipeEnabled(false);
        this.graphDay.setOnLoadListener(this.onLoadListenerDay());
        this.graphWeek.setOnLoadListener(this.onLoadListenerWeek());
        this.graphMonth.setOnLoadListener(this.onLoadListenerMonth());

        String[] tagName = getResources().getStringArray(R.array.tag_type_chart);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(graphDay, "" + tagName[0]);
        viewPagerAdapter.addFragment(graphWeek, "" + tagName[1]);
        viewPagerAdapter.addFragment(graphMonth, "" + tagName[2]);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
    }


    private void setupSelectType(int position) {
        defaultTap = position;

        this.criteriaDay.setPeriod(0);
        this.criteriaWeek.setPeriod(0);
        this.criteriaMonth.setPeriod(0);

        if (position == 0) {
            this.searchDay();
        } else if (position == 1) {
            this.searchWeek();
        } else {
            this.searchMonth();
        }
    }

    private void searchDay() {
        this.criteriaDay.setIsCanLoad(true);
        this.criteriaWeek.setIsCanLoad(false);
        this.criteriaMonth.setIsCanLoad(false);

        this.graphDay.searchGraphBloodSugar();
    }

    private void searchWeek() {
        this.criteriaDay.setIsCanLoad(false);
        this.criteriaWeek.setIsCanLoad(true);
        this.criteriaMonth.setIsCanLoad(false);

        this.graphWeek.searchGraphBloodSugar();
    }

    private void searchMonth() {
        this.criteriaDay.setIsCanLoad(false);
        this.criteriaWeek.setIsCanLoad(false);
        this.criteriaMonth.setIsCanLoad(true);

        this.graphMonth.searchGraphBloodSugar();
    }

    private GraphBloodSugarFragment.OnLoadListener onLoadListenerDay() {
        return new GraphBloodSugarFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                updateTag(0);
                viewPager.setCurrentItem(0);
            }
        };
    }

    private GraphBloodSugarFragment.OnLoadListener onLoadListenerWeek() {
        return new GraphBloodSugarFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                updateTag(1);
                viewPager.setCurrentItem(1);
            }
        };
    }

    private GraphBloodSugarFragment.OnLoadListener onLoadListenerMonth() {
        return new GraphBloodSugarFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                updateTag(2);
                viewPager.setCurrentItem(2);
            }
        };
    }


    private void updateTag(int position) {
        resetRadio();
        switch (position) {
            case 0:
                rdoSearchByDay.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchByDay.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
            case 1:
                rdoSearchByWeek.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchByWeek.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
            case 2:
                rdoSearchByMonth.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchByMonth.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
        }
    }

    private void resetRadio() {
        rdoSearchByDay.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
        rdoSearchByWeek.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
        rdoSearchByMonth.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));

        rdoSearchByDay.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
        rdoSearchByWeek.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
        rdoSearchByMonth.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
    }

    private View.OnClickListener onViewMore() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openViewAllBloodSugar(patientId, patientMenuId);
            }
        };
    }

}
