package com.truedigital.vhealth.ui.ehr.vaccinationhistory;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.GridVaccinationHistoryCriteriaObject;
import com.truedigital.vhealth.ui.adapter.ViewPagerAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.CustomViewPager;


public class VaccinationHistoryFragment extends BaseMvpFragment<VaccinationHistoryFragmentInterface.Presenter>
        implements VaccinationHistoryFragmentInterface.View {

    private CustomViewPager viewPager;
    private RadioGroup rg;
    private RadioButton rdoSearchRequireVC;
    private RadioButton rdoSearchOtherVC;

    private int patientMenuId;
    private int patientId;

    private GridVaccinationHistoryFragment necessaryVCFg;
    private GridVaccinationHistoryFragment otherVCFg;
    private ViewPagerAdapter viewPagerAdapter;

    private GridVaccinationHistoryCriteriaObject criteriaNecessaryVC;
    private GridVaccinationHistoryCriteriaObject criteriaOtherVC;

    public VaccinationHistoryFragment() {
        super();
    }

    public static VaccinationHistoryFragment newInstance(int patientId, int patientMenuId, String menuCode) {
        Bundle args = new Bundle();
        VaccinationHistoryFragment fragment = new VaccinationHistoryFragment();
        fragment.patientId = patientId;
        fragment.patientMenuId = patientMenuId;

        fragment.criteriaNecessaryVC = new GridVaccinationHistoryCriteriaObject();
        fragment.criteriaNecessaryVC.setIsCanLoad(true);
        fragment.criteriaNecessaryVC.setIsNecessary(true);
        fragment.criteriaNecessaryVC.setPatientId(patientId);
        fragment.criteriaNecessaryVC.setPatientMenuId(patientMenuId);
        fragment.criteriaNecessaryVC.setMenuCode(menuCode);

        fragment.criteriaOtherVC = new GridVaccinationHistoryCriteriaObject();
        fragment.criteriaOtherVC.setIsCanLoad(false);
        fragment.criteriaOtherVC.setIsNecessary(false);
        fragment.criteriaOtherVC.setPatientId(patientId);
        fragment.criteriaOtherVC.setPatientMenuId(patientMenuId);
        fragment.criteriaOtherVC.setMenuCode(menuCode);

        fragment.necessaryVCFg = GridVaccinationHistoryFragment.newInstance(fragment.criteriaNecessaryVC);
        fragment.otherVCFg = GridVaccinationHistoryFragment.newInstance(fragment.criteriaOtherVC);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public VaccinationHistoryFragmentInterface.Presenter createPresenter() {
        return VaccinationHistoryFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_vaccination_history;
    }

    @Override
    public void bindView(View view) {
        viewPager = (CustomViewPager) view.findViewById(R.id.viewPagerVC);
        rg = (RadioGroup) view.findViewById(R.id.radioGroupSearch);
        rdoSearchRequireVC = (RadioButton) view.findViewById(R.id.rdoSearchRequireVC);
        rdoSearchOtherVC = (RadioButton) view.findViewById(R.id.rdoSearchOtherVC);
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

        rdoSearchRequireVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSelectType(0);
            }
        });

        rdoSearchOtherVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSelectType(1);
            }
        });

        updateTag(0);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void initialize() {
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.vaccination_history), false,null, false);
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

    private void setupViewPager(CustomViewPager viewPager) {
        viewPager.setSwipeEnabled(false);
        necessaryVCFg.setOnLoadListener(this.onLoadListenerVaccinationHistoryNecessary());
        otherVCFg.setOnLoadListener(this.onLoadListenerVaccinationHistoryOther());

        String[] tagName = getResources().getStringArray(R.array.tag_type_vaccination_history);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(necessaryVCFg, "" + tagName[0]);
        viewPagerAdapter.addFragment(otherVCFg, "" + tagName[1]);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupSelectType(int position) {
        if (position == 0) {
            this.searchVaccinationHistoryNecessary();
        } else {
            this.searchVaccinationHistoryOther();
        }
    }

    private void searchVaccinationHistoryNecessary() {
        criteriaNecessaryVC.setIsCanLoad(true);
        necessaryVCFg.searchVaccinationHistory(criteriaNecessaryVC);
    }

    private void searchVaccinationHistoryOther() {
        criteriaOtherVC.setIsCanLoad(true);
        otherVCFg.searchVaccinationHistory(criteriaOtherVC);
    }


    private GridVaccinationHistoryFragment.OnLoadListener onLoadListenerVaccinationHistoryNecessary() {
        return new GridVaccinationHistoryFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                updateTag(0);
                viewPager.setCurrentItem(0);
            }

            @Override
            public void onActiveComplete(int patientMenuId) {
                criteriaNecessaryVC.setPatientMenuId(patientMenuId);
                criteriaOtherVC.setPatientMenuId(patientMenuId);
                searchVaccinationHistoryNecessary();
            }

            @Override
            public void onInActiveComplete() {
                searchVaccinationHistoryNecessary();
            }
        };
    }

    private GridVaccinationHistoryFragment.OnLoadListener onLoadListenerVaccinationHistoryOther() {
        return new GridVaccinationHistoryFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                updateTag(1);
                viewPager.setCurrentItem(1);
            }

            @Override
            public void onActiveComplete(int patientMenuId) {
                criteriaNecessaryVC.setPatientMenuId(patientMenuId);
                criteriaOtherVC.setPatientMenuId(patientMenuId);
                searchVaccinationHistoryOther();
            }

            @Override
            public void onInActiveComplete() {
                searchVaccinationHistoryOther();
            }
        };
    }

    private void updateTag(int position) {
        resetRadio();
        switch (position) {
            case 0:
                rdoSearchRequireVC.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchRequireVC.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
            case 1:
                rdoSearchOtherVC.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchOtherVC.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
        }
    }

    private void resetRadio() {
        rdoSearchRequireVC.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
        rdoSearchOtherVC.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));

        rdoSearchRequireVC.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
        rdoSearchOtherVC.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
    }


}
