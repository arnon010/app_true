package com.truedigital.vhealth.ui.ehr.medicationhistory;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.DateRangeObject;
import com.truedigital.vhealth.model.MedicationHistoryCriteriaObject;
import com.truedigital.vhealth.model.MedicationHistoryImageObject;
import com.truedigital.vhealth.model.MedicationHistoryObject;
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

public class MedicationHistoryFragment extends BaseMvpFragment<MedicationHistoryFragmentInterface.Presenter>
        implements MedicationHistoryFragmentInterface.View {

    private CustomViewPager viewPager;
    private RadioGroup rg;
    private RadioButton rdoSearchPeriod;
    private RadioButton rdoSearchDoctor;

    private int patientMenuId;
    private int patientId;

    private MedicationHistoryCriteriaObject criteriaByDate;
    private MedicationHistoryCriteriaObject criteriaByDoctor;
    private ListMedicationHistoryFragment listFgByDate;
    private ListMedicationHistoryFragment listFgByDoctor;

    private DateRangeBottomSheetFragment selectDateBottomSheet;
    private ListBottomSheetFragment selectPlaceBottomSheet;

    private ViewPagerAdapter viewPagerAdapter;

    public MedicationHistoryFragment() {
        super();
    }

    public static MedicationHistoryFragment newInstance(int patientId, int patientMenuId) {
        Bundle args = new Bundle();
        MedicationHistoryFragment fragment = new MedicationHistoryFragment();
        fragment.patientId = patientId;
        fragment.patientMenuId = patientMenuId;

        fragment.criteriaByDate = new MedicationHistoryCriteriaObject();
        fragment.criteriaByDate.setIsCanLoad(true);
        fragment.criteriaByDate.setPatientId(patientId);
        fragment.criteriaByDate.setPatientMenuId(patientMenuId);
        fragment.criteriaByDate.setPageSize(AppConstants.PAGE_SIZE);
        fragment.criteriaByDate.setPageIndex(AppConstants.START_PAGE_INDEX);

        fragment.criteriaByDoctor = new MedicationHistoryCriteriaObject();
        fragment.criteriaByDoctor.setIsCanLoad(false);
        fragment.criteriaByDoctor.setPatientId(patientId);
        fragment.criteriaByDoctor.setPatientMenuId(patientMenuId);
        fragment.criteriaByDoctor.setPageSize(AppConstants.PAGE_SIZE);
        fragment.criteriaByDoctor.setPageIndex(AppConstants.START_PAGE_INDEX);


        fragment.listFgByDate = ListMedicationHistoryFragment.newInstance(fragment.criteriaByDate);
        fragment.listFgByDoctor = ListMedicationHistoryFragment.newInstance(fragment.criteriaByDoctor);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public MedicationHistoryFragmentInterface.Presenter createPresenter() {
        return MedicationHistoryFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_medication_history;
    }

    @Override
    public void bindView(View view) {
        viewPager = (CustomViewPager) view.findViewById(R.id.viewPagerMedication);
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

        ((MainActivity) getActivity()).setAddOnClickListener(new MainActivity.OnAddClickListener() {
            @Override
            public void onAddClick() {
                MedicationHistoryObject data = new MedicationHistoryObject();
                data.setPatientId(patientId);
                data.setPatientMenuId(patientMenuId);
                data.setAttachmentList(new ArrayList<MedicationHistoryImageObject>());

                ((MainActivity) getActivity()).openNewFormMedicationHistory(data, false);
            }
        });

        updateTag(0);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void initialize() {
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.medication_history), false,null, true);
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
    public void setDefaultDateCriteria(MedicationHistoryCriteriaObject criteria) {

        criteriaByDate.setStartDate(criteria.getStartDate());
        criteriaByDate.setEndDate(criteria.getEndDate());
        this.showSelectDateCriteria();
    }

    @Override
    public void setMedicationFromSelectListCriteria(ArrayList<BottomSheetObject> criteria) {
        if (criteria.size() > 0) {
            ListBottomSheetAdapter adapter = new ListBottomSheetAdapter(getActivity());
            adapter.setOnClickListener(placeSelectOnClickListener());
            adapter.setListData(criteria);

            selectPlaceBottomSheet = ListBottomSheetFragment.newInstance(R.layout.fragment_list_bottom_sheet, (int) CommonUtils.convertDpToPixel(getActivity(), 200), adapter);
            selectPlaceBottomSheet.show(getActivity().getSupportFragmentManager(), selectPlaceBottomSheet.getTag());
        } else {
            searchMedicationHistoryCriteriaByDoctor("");
        }
    }

    public void clearList() {
        if (viewPager.getCurrentItem() == 0) {
            criteriaByDate.setPageIndex(AppConstants.START_PAGE_INDEX);
            listFgByDate.clearTempList();
        } else {
            criteriaByDoctor.setPageIndex(AppConstants.START_PAGE_INDEX);
            listFgByDoctor.clearTempList();
        }
    }

    private void setupViewPager(CustomViewPager viewPager) {
        viewPager.setSwipeEnabled(false);
        listFgByDate.setOnLoadListener(this.onLoadListenerMedicationHistoryByDate());
        listFgByDoctor.setOnLoadListener(this.onLoadListenerMedicationHistoryByPlace());

        String[] tagName = getResources().getStringArray(R.array.tag_type_medication_history);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(listFgByDate, "" + tagName[0]);
        viewPagerAdapter.addFragment(listFgByDoctor, "" + tagName[1]);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupSelectType(int position) {
        if (position == 0) {
            if (criteriaByDate.getStartDate() == null && criteriaByDate.getEndDate() == null) {
                getPresenter().getDefaultDateCriteria(this.patientId, this.patientMenuId);
            } else {
                this.showSelectDateCriteria();
            }
        } else {
            getPresenter().getMedicationFromSelectList(this.patientId, this.patientMenuId);
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

                searchMedicationHistoryCriteriaByDate(data.getStartDate(), data.getEndDate());
            }
        };
    }

    private ListBottomSheetAdapter.OnItemClickListener placeSelectOnClickListener() {
        return new ListBottomSheetAdapter.OnItemClickListener<BottomSheetObject>() {
            @Override
            public void onItemClick(BottomSheetObject data) {
                selectPlaceBottomSheet.dismiss();

                searchMedicationHistoryCriteriaByDoctor(data.getValueItem());
            }
        };
    }

    private void searchMedicationHistoryCriteriaByDate(Date startDate, Date endDate) {
        criteriaByDate.setIsCanLoad(true);
        criteriaByDate.setPageIndex(AppConstants.START_PAGE_INDEX);
        criteriaByDate.setPageSize(AppConstants.PAGE_SIZE);
        criteriaByDate.setStartDate(startDate);
        criteriaByDate.setEndDate(endDate);

        listFgByDate.searchMedicationHistory(criteriaByDate);
    }

    private void searchMedicationHistoryCriteriaByDoctor(String place) {
        criteriaByDoctor.setIsCanLoad(true);
        criteriaByDoctor.setPageIndex(AppConstants.START_PAGE_INDEX);
        criteriaByDoctor.setPageSize(AppConstants.PAGE_SIZE);
        criteriaByDoctor.setPlace(place);

        listFgByDoctor.searchMedicationHistory(criteriaByDoctor);
    }

    private ListMedicationHistoryFragment.OnLoadListener onLoadListenerMedicationHistoryByDate() {
        return new ListMedicationHistoryFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                updateTag(0);
                viewPager.setCurrentItem(0);
            }
        };
    }

    private ListMedicationHistoryFragment.OnLoadListener onLoadListenerMedicationHistoryByPlace() {
        return new ListMedicationHistoryFragment.OnLoadListener() {
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
