package com.truedigital.vhealth.ui.ehr.pregnanthistory;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.DateRangeObject;
import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.model.PregnantHistoryCriteriaObject;
import com.truedigital.vhealth.model.PregnantHistoryImageObject;
import com.truedigital.vhealth.model.PregnantHistoryObject;
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

public class PregnantHistoryFragment extends BaseMvpFragment<PregnantHistoryFragmentInterface.Presenter>
        implements PregnantHistoryFragmentInterface.View {

    private CustomViewPager viewPager;
    private RadioGroup rg;
    private RadioButton rdoSearchPeriod;
    private RadioButton rdoSearchPlace;

    private int patientMenuId;
    private int patientId;
    private String menuName = "";

    private PregnantHistoryCriteriaObject criteriaByDate;
    private PregnantHistoryCriteriaObject criteriaByPlace;
    private ListPregnantHistoryFragment listFgByDate;
    private ListPregnantHistoryFragment listFgByPlace;

    private DateRangeBottomSheetFragment selectDateBottomSheet;
    private ListBottomSheetFragment selectPlaceBottomSheet;
    private ViewPagerAdapter viewPagerAdapter;

    public PregnantHistoryFragment() {
        super();
    }

    public static PregnantHistoryFragment newInstance(int patientId, int patientMenuId) {
        Bundle args = new Bundle();
        PregnantHistoryFragment fragment = new PregnantHistoryFragment();
        fragment.patientId = patientId;
        fragment.patientMenuId = patientMenuId;

        fragment.criteriaByDate = new PregnantHistoryCriteriaObject();
        fragment.criteriaByDate.setIsCanLoad(true);
        fragment.criteriaByDate.setPatientId(patientId);
        fragment.criteriaByDate.setPatientMenuId(patientMenuId);
        fragment.criteriaByDate.setPageSize(AppConstants.PAGE_SIZE);
        fragment.criteriaByDate.setPageIndex(AppConstants.START_PAGE_INDEX);

        fragment.criteriaByPlace = new PregnantHistoryCriteriaObject();
        fragment.criteriaByPlace.setIsCanLoad(false);
        fragment.criteriaByPlace.setPatientId(patientId);
        fragment.criteriaByPlace.setPatientMenuId(patientMenuId);
        fragment.criteriaByPlace.setPageSize(AppConstants.PAGE_SIZE);
        fragment.criteriaByPlace.setPageIndex(AppConstants.START_PAGE_INDEX);

        fragment.listFgByDate = ListPregnantHistoryFragment.newInstance(fragment.criteriaByDate);
        fragment.listFgByPlace = ListPregnantHistoryFragment.newInstance(fragment.criteriaByPlace);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public PregnantHistoryFragmentInterface.Presenter createPresenter() {
        return PregnantHistoryFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_pregnant_history;
    }

    @Override
    public void bindView(View view) {
        viewPager = (CustomViewPager) view.findViewById(R.id.viewPagerPregnant);
        rg = (RadioGroup) view.findViewById(R.id.radioGroupSearch);
        rdoSearchPeriod = (RadioButton) view.findViewById(R.id.rdoSearchPeriod);
        rdoSearchPlace = (RadioButton) view.findViewById(R.id.rdoSearchPlace);
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

        rdoSearchPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSelectType(1);
            }
        });

        ((MainActivity) getActivity()).setAddOnClickListener(new MainActivity.OnAddClickListener() {
            @Override
            public void onAddClick() {
                PregnantHistoryObject data = new PregnantHistoryObject();
                data.setPatientId(patientId);
                data.setPatientMenuId(patientMenuId);
                data.setAttachmentList(new ArrayList<PregnantHistoryImageObject>());

                ((MainActivity) getActivity()).openNewFormPregnantHistory(data, false);
            }
        });

        updateTag(0);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void initialize() {
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);

        if (this.menuName == null || this.menuName.equals("")) {
            getPresenter().getPatientMenu(patientMenuId);
        }

        this.setNameToolbar();
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
    public void setPatientMenu(EhrMenuObject menu) {
        this.menuName = menu.getMenuName();
        this.setNameToolbar();
    }

    @Override
    public void setDefaultDateCriteria(PregnantHistoryCriteriaObject criteria) {

        criteriaByDate.setStartDate(criteria.getStartDate());
        criteriaByDate.setEndDate(criteria.getEndDate());
        this.showSelectDateCriteria();
    }

    @Override
    public void setPlaceSelectListCriteria(ArrayList<BottomSheetObject> criteria) {
        if (criteria.size() > 0) {
            ListBottomSheetAdapter adapter = new ListBottomSheetAdapter(getActivity());
            adapter.setOnClickListener(placeSelectOnClickListener());
            adapter.setListData(criteria);

            selectPlaceBottomSheet = ListBottomSheetFragment.newInstance(R.layout.fragment_list_bottom_sheet, (int) CommonUtils.convertDpToPixel(getActivity(), 200), adapter);
            selectPlaceBottomSheet.show(getActivity().getSupportFragmentManager(), selectPlaceBottomSheet.getTag());
        } else {
            searchPregnantHistoryCriteriaByDoctor("");
        }
    }

    public void clearList() {
        if (viewPager.getCurrentItem() == 0) {
            criteriaByDate.setPageIndex(AppConstants.START_PAGE_INDEX);
            listFgByDate.clearTempList();
        } else {
            criteriaByPlace.setPageIndex(AppConstants.START_PAGE_INDEX);
            listFgByPlace.clearTempList();
        }
    }

    private void setNameToolbar() {
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.pregnant_history) +"\n" + this.menuName, false,null, true);
    }

    private void setupViewPager(CustomViewPager viewPager) {
        viewPager.setSwipeEnabled(false);
        listFgByDate.setOnLoadListener(this.onLoadListenerPregnantHistoryByDate());
        listFgByPlace.setOnLoadListener(this.onLoadListenerPregnantHistoryByPlace());

        String[] tagName = getResources().getStringArray(R.array.tag_type_pregnant_history);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(listFgByDate, "" + tagName[0]);
        viewPagerAdapter.addFragment(listFgByPlace, "" + tagName[1]);
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
            getPresenter().getPlaceSelectList(this.patientId, this.patientMenuId);
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

                searchPregnantHistoryCriteriaByDate(data.getStartDate(), data.getEndDate());
            }
        };
    }

    private ListBottomSheetAdapter.OnItemClickListener placeSelectOnClickListener() {
        return new ListBottomSheetAdapter.OnItemClickListener<BottomSheetObject>() {
            @Override
            public void onItemClick(BottomSheetObject data) {
                selectPlaceBottomSheet.dismiss();

                searchPregnantHistoryCriteriaByDoctor(data.getValueItem());
            }
        };
    }

    private void searchPregnantHistoryCriteriaByDate(Date startDate, Date endDate) {
        criteriaByDate.setIsCanLoad(true);
        criteriaByDate.setPageIndex(AppConstants.START_PAGE_INDEX);
        criteriaByDate.setPageSize(AppConstants.PAGE_SIZE);
        criteriaByDate.setStartDate(startDate);
        criteriaByDate.setEndDate(endDate);

        listFgByDate.searchPregnantHistory(criteriaByDate);
    }

    private void searchPregnantHistoryCriteriaByDoctor(String place) {
        criteriaByPlace.setIsCanLoad(true);
        criteriaByPlace.setPageIndex(AppConstants.START_PAGE_INDEX);
        criteriaByPlace.setPageSize(AppConstants.PAGE_SIZE);
        criteriaByPlace.setPlace(place);

        listFgByPlace.searchPregnantHistory(criteriaByPlace);
    }

    private ListPregnantHistoryFragment.OnLoadListener onLoadListenerPregnantHistoryByDate() {
        return new ListPregnantHistoryFragment.OnLoadListener() {
            @Override
            public void onLoadComplete() {
                updateTag(0);
                viewPager.setCurrentItem(0);
            }
        };
    }

    private ListPregnantHistoryFragment.OnLoadListener onLoadListenerPregnantHistoryByPlace() {
        return new ListPregnantHistoryFragment.OnLoadListener() {
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
                rdoSearchPlace.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdoSearchPlace.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                break;
        }
    }

    private void resetRadio() {
        rdoSearchPeriod.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
        rdoSearchPlace.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));

        rdoSearchPeriod.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
        rdoSearchPlace.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
    }

}
