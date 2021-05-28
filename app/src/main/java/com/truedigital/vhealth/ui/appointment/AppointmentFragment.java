package com.truedigital.vhealth.ui.appointment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.ui.adapter.ListBottomSheetAdapter;
import com.truedigital.vhealth.ui.adapter.ViewPagerAdapter;
import com.truedigital.vhealth.ui.appointment.list.ListAppointmentFragment;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.ui.dialog.ListBottomSheetFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppointmentUtil;
import com.truedigital.vhealth.utils.CommonUtils;

import java.util.ArrayList;

public class AppointmentFragment extends BaseMvpFragment<AppointmentFragmentInterface.Presenter>
        implements AppointmentFragmentInterface.View {

    private ViewPager viewPager;
    private RadioGroup rg;
    private RadioButton rdButton0;
    private RadioButton rdButton1;
    private ListBottomSheetFragment bottomSheetFragment;
    private ListAppointmentFragment listAppointmentFragmentTime;
    private ListAppointmentFragment listAppointmentFragmentDoctor;

    public AppointmentFragment() {
        super();
    }

    public static AppointmentFragment newInstance() {
        AppointmentFragment fragment = new AppointmentFragment();
        fragment.listAppointmentFragmentTime = ListAppointmentFragment.newInstance(0);
        fragment.listAppointmentFragmentDoctor = ListAppointmentFragment.newInstance(1);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkToken();
    }

    @Override
    public AppointmentFragmentInterface.Presenter createPresenter() {
        return AppointmentFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_appointment;
    }

    @Override
    public void bindView(View view) {
        viewPager = view.findViewById(R.id.viewPagerDoctor);
        rg = view.findViewById(R.id.radioGroupSearch);
        rdButton0 = view.findViewById(R.id.radio0);
        rdButton1 = view.findViewById(R.id.radio1);
    }

    @Override
    public void setupInstance() {
        showToolbar();
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.appointment_title, false);
    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuAppointmentSelected();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateTag(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setCurrentItem(0);

        rdButton0.setOnClickListener(view -> setupSelectType(0));
        rdButton1.setOnClickListener(view -> setupSelectType(1));

        updateView();
    }

    private void updateView() {
        setupViewPager(viewPager);
        updateTag(0);
    }

    private void checkToken() {
        String token = AppManager.getDataManager().getAccess_token();
        getPresenter().onCheckToken(token, new BaseMvpPresenter.OnTokenListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail() {
            }
        });
    }

    @Override
    public void initialize() {
    }

    private void setupViewPager(ViewPager viewPager) {
        String[] tagName = getResources().getStringArray(R.array.tag_type);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(listAppointmentFragmentTime, "" + tagName[0]);
        adapter.addFragment(listAppointmentFragmentDoctor, "" + tagName[1]);
        viewPager.setAdapter(adapter);
    }

    private void setupSelectType(int position) {
        if (position == 0) {
            setShowBottomSortTime();
        } else if (position == 1) {
            setShowBottomSortDoctor();
        }
        updateTag(position);
        viewPager.setCurrentItem(position);
    }

    private void updateTag(int position) {
        int iconArrowDown = R.drawable.ic_arror_down_white;
        resetRadio();
        switch (position) {
            case 0:
                rdButton0.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                rdButton0.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconArrowDown, 0);
                break;
            case 1:
                rdButton1.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                rdButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_select));
                rdButton1.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconArrowDown, 0);
                break;
        }
    }

    private void resetRadio() {
        rdButton0.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_red));
        rdButton1.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_red));

        rdButton0.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));
        rdButton1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.tag_button_unselect));

        rdButton0.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        rdButton1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void setShowBottomSortTime() {
        ArrayList<BottomSheetObject> criteria = new ArrayList<>();
        BottomSheetObject obj = new BottomSheetObject();
        obj.setDescriptions(getString(AppointmentUtil.getTagTitle(0)));
        obj.setValueItem("0");
        criteria.add(obj);
        obj = new BottomSheetObject();
        obj.setDescriptions(getString(AppointmentUtil.getTagTitle(1)));
        obj.setValueItem("1");
        criteria.add(obj);
        obj = new BottomSheetObject();
        obj.setDescriptions(getString(AppointmentUtil.getTagTitle(2)));
        obj.setValueItem("2");
        criteria.add(obj);
        showBottomSortTime(criteria);
    }

    private void setShowBottomSortDoctor() {
        ArrayList<BottomSheetObject> criteria = new ArrayList<>();
        BottomSheetObject obj = new BottomSheetObject();
        obj.setDescriptions(getString(R.string.appointment_tag_favorite));
        obj.setValueItem("0");
        criteria.add(obj);
        obj = new BottomSheetObject();
        obj.setDescriptions(getString(R.string.appointment_tag_every));
        obj.setValueItem("1");
        criteria.add(obj);
        showBottomSortDoctor(criteria);
    }

    int sheetType = 0;

    public void showBottomSortTime(ArrayList<BottomSheetObject> criteria) {
        sheetType = 0;
        ListBottomSheetAdapter adapter = new ListBottomSheetAdapter(getActivity());
        adapter.setOnClickListener(onSelectAppointmentClickListener());
        adapter.setListData(criteria);
        bottomSheetFragment = ListBottomSheetFragment.newInstance(R.layout.fragment_list_bottom_sheet, (int) CommonUtils.convertDpToPixel(getActivity(), 200), adapter);
        bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void showBottomSortDoctor(ArrayList<BottomSheetObject> criteria) {
        sheetType = 1;
        ListBottomSheetAdapter adapter = new ListBottomSheetAdapter(getActivity());
        adapter.setOnClickListener(onSelectAppointmentClickListener());
        adapter.setListData(criteria);
        bottomSheetFragment = ListBottomSheetFragment.newInstance(R.layout.fragment_list_bottom_sheet, (int) CommonUtils.convertDpToPixel(getActivity(), 200), adapter);
        bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private ListBottomSheetAdapter.OnItemClickListener onSelectAppointmentClickListener() {
        return (ListBottomSheetAdapter.OnItemClickListener<BottomSheetObject>) data -> {
            bottomSheetFragment.dismiss();
            if (sheetType == 0) onSearcAppointmentByTime(data.getValueItem());
            if (sheetType == 1) onSearcAppointmentByDoctor(data.getValueItem());
        };
    }

    private void onSearcAppointmentByTime(String valueItem) {
        this.listAppointmentFragmentTime.searchByTime(0, Integer.parseInt(valueItem));
    }

    private void onSearcAppointmentByDoctor(String valueItem) {
        this.listAppointmentFragmentDoctor.searchByDoctor(1, Integer.parseInt(valueItem));
    }
}
