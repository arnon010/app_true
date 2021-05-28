package com.truedigital.vhealth.ui.ehr.doctornote;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.DateRangeObject;
import com.truedigital.vhealth.model.DoctorNoteCriteriaObject;
import com.truedigital.vhealth.ui.adapter.ListBottomSheetAdapter;
import com.truedigital.vhealth.ui.adapter.ViewPagerAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.dialog.DateRangeBottomSheetFragment;
import com.truedigital.vhealth.ui.dialog.ListBottomSheetFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.CustomViewPager;

import java.util.ArrayList;
import java.util.Date;

public class DoctorNoteFragment extends BaseMvpFragment<DoctorNoteFragmentInterface.Presenter> implements DoctorNoteFragmentInterface.View {

    private CustomViewPager viewPager;
    private RadioGroup rg;
    private RadioButton rdoSearchPeriod;
    private RadioButton rdoSearchDoctor;

    private int patientMenuId;
    private int patientId;

    private DoctorNoteCriteriaObject criteriaByDate;
    private DoctorNoteCriteriaObject criteriaByDoctor;
    private ListDoctorNoteFragment listFgByDate;
    private ListDoctorNoteFragment listFgByDoctor;

    private DateRangeBottomSheetFragment selectDateBottomSheet;
    private ListBottomSheetFragment selectDoctorBottomSheet;

    private ViewPagerAdapter viewPagerAdapter;

    private int pageSize = AppConstants.PAGE_SIZE;
    private int startPageIndex = AppConstants.START_PAGE_INDEX;

    public DoctorNoteFragment() {
        super();
    }

    public static DoctorNoteFragment newInstance(int patientId, int patientMenuId) {
        Bundle args = new Bundle();
        DoctorNoteFragment fragment = new DoctorNoteFragment();
        fragment.patientId = patientId;
        fragment.patientMenuId = patientMenuId;

        fragment.criteriaByDate = new DoctorNoteCriteriaObject();
        fragment.criteriaByDate.setIsCanLoad(true);
        fragment.criteriaByDate.setPatientId(patientId);
        fragment.criteriaByDate.setPageSize(fragment.pageSize);
        fragment.criteriaByDate.setPageIndex(fragment.startPageIndex);

        fragment.criteriaByDoctor = new DoctorNoteCriteriaObject();
        fragment.criteriaByDoctor.setIsCanLoad(false);
        fragment.criteriaByDoctor.setPatientId(patientId);
        fragment.criteriaByDoctor.setPageSize(fragment.pageSize);
        fragment.criteriaByDoctor.setPageIndex(fragment.startPageIndex);

        fragment.listFgByDate = ListDoctorNoteFragment.newInstance(fragment.criteriaByDate);
        fragment.listFgByDoctor = ListDoctorNoteFragment.newInstance(fragment.criteriaByDoctor);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public DoctorNoteFragmentInterface.Presenter createPresenter() {
        return DoctorNoteFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_doctor_note;
    }

    @Override
    public void bindView(View view) {
        viewPager = (CustomViewPager) view.findViewById(R.id.viewPagerDoctor);
        rg = (RadioGroup) view.findViewById(R.id.radioGroupSearch);
        rdoSearchPeriod = (RadioButton) view.findViewById(R.id.rdoSearchPeriod);
        rdoSearchDoctor = (RadioButton) view.findViewById(R.id.rdoSearchDoctor);
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

        rdoSearchPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSelectType(0);
            }
        });

        rdoSearchDoctor.setOnClickListener(new View.OnClickListener() {
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
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.doctor_note), false,null, false);
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
    public void setDefaultDateCriteria(DoctorNoteCriteriaObject criteria) {

        criteriaByDate.setStartDate(criteria.getStartDate());
        criteriaByDate.setEndDate(criteria.getEndDate());
        this.showSelectDateCriteria();
    }

    @Override
    public void setDoctorSelectListCriteria(ArrayList<BottomSheetObject> criteria) {

        if (criteria.size() > 0) {
            ListBottomSheetAdapter adapter = new ListBottomSheetAdapter(getActivity());
            adapter.setOnClickListener(doctorSelectOnClickListener());
            adapter.setListData(criteria);

            selectDoctorBottomSheet = ListBottomSheetFragment.newInstance(R.layout.fragment_list_bottom_sheet, (int) CommonUtils.convertDpToPixel(getActivity(), 200), adapter);
            selectDoctorBottomSheet.show(getActivity().getSupportFragmentManager(), selectDoctorBottomSheet.getTag());
        } else {
            searchDoctorCriteriaByDoctor(0);
        }
    }

    private void setupViewPager(CustomViewPager viewPager) {
        viewPager.setSwipeEnabled(false);
        listFgByDate.setOnLoadListener(this.onLoadListenerSearchDoctorByDate());
        listFgByDoctor.setOnLoadListener(this.onLoadListenerSearchDoctorByDoctor());

        String[] tagName = getResources().getStringArray(R.array.tag_type_doctor_note);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(listFgByDate, "" + tagName[0]);
        viewPagerAdapter.addFragment(listFgByDoctor, "" + tagName[1]);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupSelectType(int position) {
        if (position == 0) {
            if (criteriaByDate.getStartDate() == null && criteriaByDate.getEndDate() == null) {
                getPresenter().getDefaultDateCriteria(this.patientId);
            } else {
                this.showSelectDateCriteria();
            }
        } else {
            getPresenter().getDoctorSelectList(this.patientId);
        }
    }

    private void showSelectDateCriteria() {
        selectDateBottomSheet = DateRangeBottomSheetFragment.newInstance(getActivity(), (int) CommonUtils.convertDpToPixel(getActivity(), 230), criteriaByDate.getStartDate(), criteriaByDate.getEndDate());
        selectDateBottomSheet.setOnConfirmListener(dateRangeOnConfirm());
        selectDateBottomSheet.show(getActivity().getSupportFragmentManager(), selectDateBottomSheet.getTag());
    }

    private DateRangeBottomSheetFragment.OnConfirmListener dateRangeOnConfirm() {
        return new DateRangeBottomSheetFragment.OnConfirmListener() {
            @Override
            public void onConfirm(DateRangeObject data) {
                selectDateBottomSheet.dismiss();

                searchDoctorCriteriaByDate(data.getStartDate(), data.getEndDate());
            }
        };
    }

    private ListBottomSheetAdapter.OnItemClickListener doctorSelectOnClickListener() {
        return new ListBottomSheetAdapter.OnItemClickListener<BottomSheetObject>() {
            @Override
            public void onItemClick(BottomSheetObject data) {
                selectDoctorBottomSheet.dismiss();

                searchDoctorCriteriaByDoctor(Integer.parseInt(data.getValueItem()));
            }
        };
    }

    private void searchDoctorCriteriaByDate(Date startDate, Date endDate) {
        criteriaByDate.setIsCanLoad(true);
        criteriaByDate.setPageIndex(this.startPageIndex);
        criteriaByDate.setPageSize(this.pageSize);
        criteriaByDate.setStartDate(startDate);
        criteriaByDate.setEndDate(endDate);

        listFgByDate.searchDoctor(criteriaByDate);

    }

    private void searchDoctorCriteriaByDoctor(int doctorId) {
        criteriaByDoctor.setIsCanLoad(true);
        criteriaByDoctor.setPageIndex(this.startPageIndex);
        criteriaByDoctor.setPageSize(this.pageSize);
        criteriaByDoctor.setDoctorId(doctorId);

        listFgByDoctor.searchDoctor(criteriaByDoctor);
    }

    private ListDoctorNoteFragment.OnLoadListener onLoadListenerSearchDoctorByDate() {
        return new ListDoctorNoteFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                updateTag(0);
                viewPager.setCurrentItem(0);
            }
        };
    }

    private ListDoctorNoteFragment.OnLoadListener onLoadListenerSearchDoctorByDoctor() {
        return new ListDoctorNoteFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                updateTag(1);
                viewPager.setCurrentItem(1);
            }
        };
    }

    private void updateTag(int position) {
        resetRadio();
        switch (position) {
            case 0:
                rdoSearchPeriod.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchPeriod.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
            case 1:
                rdoSearchDoctor.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchDoctor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
        }
    }

    private void resetRadio() {
        rdoSearchPeriod.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
        rdoSearchDoctor.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));

        rdoSearchPeriod.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
        rdoSearchDoctor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
    }


}
