package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.DateRangeObject;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.Date;

public class DateRangeBottomSheetFragment extends BottomSheetDialogFragment {

    private int height;
    private DateRangeObject criteria;
    private Context context;

    private RelativeLayout relativeStartDate;
    private RelativeLayout relativeEndDate;
    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView btnConfirm;
    private DateRangeBottomSheetFragment.OnConfirmListener onConfirmListener;

    private CalendarBottomSheetFragment selectStartDateBottomSheet;
    private CalendarBottomSheetFragment selectEndDateBottomSheet;

    public static DateRangeBottomSheetFragment newInstance(Context context, int height) {
        DateRangeBottomSheetFragment fragment = new DateRangeBottomSheetFragment();
        fragment.height = height;
        fragment.criteria = new DateRangeObject();
        fragment.context = context;

        return fragment;
    }

    public static DateRangeBottomSheetFragment newInstance(Context context, int height, Date startDate, Date endDate) {
        DateRangeBottomSheetFragment fragment = new DateRangeBottomSheetFragment();
        fragment.height = height;
        fragment.criteria = new DateRangeObject();
        fragment.criteria.setStartDate(startDate);
        fragment.criteria.setEndDate(endDate);
        fragment.context = context;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View inflatedView = View.inflate(getContext(), R.layout.fragment_date_range_bottom_sheet, null);
        this.setupView(inflatedView);

        dialog.setContentView(inflatedView);

        View parent = (View) inflatedView.getParent();
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        bottomSheetBehavior.setPeekHeight(this.height);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        params.height = this.height;
        parent.setLayoutParams(params);
    }

    private void setupView(View view){
        relativeStartDate = (RelativeLayout) view.findViewById(R.id.relativeStartDate);
        relativeEndDate   = (RelativeLayout) view.findViewById(R.id.relativeEndDate);
        txtStartDate = (TextView) view.findViewById(R.id.txtStartDate);
        txtEndDate = (TextView) view.findViewById(R.id.txtEndDate);
        btnConfirm = (TextView) view.findViewById(R.id.btnConfirm);

        txtStartDate.setText(ConvertDate.convertDateSimpleShow(criteria.getStartDate()));
        txtEndDate.setText(ConvertDate.convertDateSimpleShow(criteria.getEndDate()));

        StringUtils.setUnderline(btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmListener.onConfirm(criteria);
            }
        });

        relativeStartDate.setOnClickListener(selectStartDateClickListener());
        relativeEndDate.setOnClickListener(selectEndDateClickListener());
    }

    private View.OnClickListener selectStartDateClickListener()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectStartDateBottomSheet = CalendarBottomSheetFragment.newInstance(context, (int) CommonUtils.convertDpToPixel(getActivity(),370), criteria.getStartDate());
                selectStartDateBottomSheet.show(getActivity().getSupportFragmentManager(), selectStartDateBottomSheet.getTag());
                selectStartDateBottomSheet.setOnDateChangeListener(new CalendarBottomSheetFragment.OnDateChangeListener() {
                    @Override
                    public void onDateChange(Date date) {
                        selectStartDateBottomSheet.dismiss();
                        criteria.setStartDate(date);
                        txtStartDate.setText(ConvertDate.convertDateSimpleShow(date));
                    }
                });
            }
        };
    }

    private View.OnClickListener selectEndDateClickListener()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectEndDateBottomSheet = CalendarBottomSheetFragment.newInstance(context, (int) CommonUtils.convertDpToPixel(getActivity(),370), criteria.getEndDate());
                selectEndDateBottomSheet.show(getActivity().getSupportFragmentManager(), selectEndDateBottomSheet.getTag());
                selectEndDateBottomSheet.setOnDateChangeListener(new CalendarBottomSheetFragment.OnDateChangeListener() {
                    @Override
                    public void onDateChange(Date date) {
                        selectEndDateBottomSheet.dismiss();
                        criteria.setEndDate(date);
                        txtEndDate.setText(ConvertDate.convertDateSimpleShow(date));
                    }
                });
            }
        };
    }

    public interface OnConfirmListener {
        void onConfirm(DateRangeObject data);
    }

    public void setOnConfirmListener(DateRangeBottomSheetFragment.OnConfirmListener OnConfirmListener) {
        this.onConfirmListener = OnConfirmListener;
    }
}
