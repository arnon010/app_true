package com.truedigital.vhealth.ui.ehr.laboratory;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.GridLaboratoryChiiwiiCriteriaObject;
import com.truedigital.vhealth.model.LaboratoryOtherCriteriaObject;
import com.truedigital.vhealth.model.LaboratoryOtherImageObject;
import com.truedigital.vhealth.model.LaboratoryOtherObject;
import com.truedigital.vhealth.ui.adapter.ViewPagerAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CustomViewPager;

import java.util.ArrayList;

public class LaboratoryFragment extends BaseMvpFragment<LaboratoryFragmentInterface.Presenter>
        implements LaboratoryFragmentInterface.View {

    private CustomViewPager viewPager;
    private RadioGroup rg;
    private RadioButton rdoSearchLabChiiwii;
    private RadioButton rdoSearchLabOther;

    private int patientMenuId;
    private int patientId;
    private int defaultTap;

    private GridLaboratoryChiiwiiCriteriaObject criteriaLabChiiwii;
    private LaboratoryOtherCriteriaObject criteriaLabOther;
    private GridLaboratoryChiiwiiFragment labChiiwiiFg;
    private ListLaboratoryOtherFragment labOtherFg;

    private ViewPagerAdapter viewPagerAdapter;

    public LaboratoryFragment() {
        super();
    }

    public static LaboratoryFragment newInstance(int patientId, int patientMenuId, int defaultTap) {
        Bundle args = new Bundle();
        LaboratoryFragment fragment = new LaboratoryFragment();
        fragment.patientId = patientId;
        fragment.patientMenuId = patientMenuId;
        fragment.defaultTap = defaultTap;

        fragment.criteriaLabChiiwii = new GridLaboratoryChiiwiiCriteriaObject();
        fragment.criteriaLabChiiwii.setPatientId(patientId);
        fragment.criteriaLabChiiwii.setPatientMenuId(patientMenuId);

        fragment.criteriaLabOther = new LaboratoryOtherCriteriaObject();
        fragment.criteriaLabOther.setPatientId(patientId);
        fragment.criteriaLabOther.setPatientMenuId(patientMenuId);
        fragment.criteriaLabOther.setPageSize(AppConstants.PAGE_SIZE);
        fragment.criteriaLabOther.setPageIndex(AppConstants.START_PAGE_INDEX);

        fragment.setCanLoad();

        fragment.labChiiwiiFg = GridLaboratoryChiiwiiFragment.newInstance(fragment.criteriaLabChiiwii);
        fragment.labOtherFg = ListLaboratoryOtherFragment.newInstance(fragment.criteriaLabOther);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public LaboratoryFragmentInterface.Presenter createPresenter() {
        return LaboratoryFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_laboratory;
    }

    @Override
    public void bindView(View view) {
        viewPager = (CustomViewPager) view.findViewById(R.id.viewPagerLab);
        rg = (RadioGroup) view.findViewById(R.id.radioGroupSearch);
        rdoSearchLabChiiwii = (RadioButton) view.findViewById(R.id.rdoSearchLabChiiwii);
        rdoSearchLabOther = (RadioButton) view.findViewById(R.id.rdoSearchLabOther);
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

        rdoSearchLabChiiwii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSelectType(0);
            }
        });

        rdoSearchLabOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSelectType(1);
            }
        });

        ((MainActivity) getActivity()).setAddOnClickListener(new MainActivity.OnAddClickListener() {
            @Override
            public void onAddClick() {
                LaboratoryOtherObject data = new LaboratoryOtherObject();
                data.setPatientId(patientId);
                data.setPatientMenuId(patientMenuId);
                data.setAttachmentList(new ArrayList<LaboratoryOtherImageObject>());

                ((MainActivity) getActivity()).openNewFormLaboratoryOther(data, false);
            }
        });

        setCanLoad();
        updateTag(defaultTap);
        viewPager.setCurrentItem(defaultTap);
    }

    @Override
    public void initialize() {
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);

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

    private void setCanLoad() {
        if (defaultTap == 0) {
            this.criteriaLabChiiwii.setIsCanLoad(true);
            this.criteriaLabOther.setIsCanLoad(false);
        } else {
            this.criteriaLabChiiwii.setIsCanLoad(false);
            this.criteriaLabOther.setIsCanLoad(true);
        }
    }

    public void clearList() {
        setCanLoad();

        if (viewPager.getCurrentItem() == 1) {
            criteriaLabOther.setPageIndex(AppConstants.START_PAGE_INDEX);
            labOtherFg.clearTempList();
        }
    }

    private void setupViewPager(CustomViewPager viewPager) {
        viewPager.setSwipeEnabled(false);
        labChiiwiiFg.setOnLoadListener(this.onLoadListenerLabChiiwii());
        labOtherFg.setOnLoadListener(this.onLoadListenerLabOther());

        String[] tagName = getResources().getStringArray(R.array.tag_type_laboratory);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(labChiiwiiFg, "" + tagName[0]);
        viewPagerAdapter.addFragment(labOtherFg, "" + tagName[1]);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupSelectType(int position) {
        if (position == 0) {
            this.searchLabChiiwii();
        } else {
            this.searchLabOther();
        }
    }

    private void searchLabChiiwii() {
        criteriaLabChiiwii.setIsCanLoad(true);

        labChiiwiiFg.searchLaboratoryChiiwii(criteriaLabChiiwii);
    }

    private void searchLabOther() {
        criteriaLabOther.setIsCanLoad(true);
        criteriaLabOther.setPageIndex(AppConstants.START_PAGE_INDEX);
        criteriaLabOther.setPageSize(AppConstants.PAGE_SIZE);

        labOtherFg.searchLaboratoryOther(criteriaLabOther);
    }

    private GridLaboratoryChiiwiiFragment.OnLoadListener onLoadListenerLabChiiwii() {
        return new GridLaboratoryChiiwiiFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                defaultTap = 0;
                updateTag(0);
                viewPager.setCurrentItem(0);
            }
        };
    }

    private ListLaboratoryOtherFragment.OnLoadListener onLoadListenerLabOther() {
        return new ListLaboratoryOtherFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                defaultTap = 1;
                updateTag(1);
                viewPager.setCurrentItem(1);
            }
        };
    }


    private void updateTag(int position) {
        resetRadio();
        switch (position) {
            case 0:
                ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.laboratory), false,null, false);
                rdoSearchLabChiiwii.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchLabChiiwii.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
            case 1:
                ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.laboratory), false,null, true);
                rdoSearchLabOther.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchLabOther.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
        }
    }

    private void resetRadio() {
        rdoSearchLabChiiwii.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
        rdoSearchLabOther.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));

        rdoSearchLabChiiwii.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
        rdoSearchLabOther.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
    }


}
