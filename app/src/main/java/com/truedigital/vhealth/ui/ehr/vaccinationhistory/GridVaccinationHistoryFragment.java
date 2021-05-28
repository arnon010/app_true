package com.truedigital.vhealth.ui.ehr.vaccinationhistory;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.GridVaccinationHistoryCriteriaObject;
import com.truedigital.vhealth.model.VaccinationHistoryObject;
import com.truedigital.vhealth.model.RowHeaderVaccineObject;
import com.truedigital.vhealth.model.GridVaccinationHistoryObject;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.GridMatrixLayout;


public class GridVaccinationHistoryFragment extends BaseMvpFragment<GridVaccinationHistoryFragmentInterface.Presenter>
        implements GridVaccinationHistoryFragmentInterface.View {

    private LinearLayout mainView;

    private GridVaccinationHistoryCriteriaObject criteria;
    private GridVaccinationHistoryObject data;
    private GridVaccinationHistoryFragment.OnLoadListener onLoadListener;

    public interface OnLoadListener {
        void onLoadComplete();

        void onActiveComplete(int patientMenuId);

        void onInActiveComplete();
    }

    public void setOnLoadListener(GridVaccinationHistoryFragment.OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public GridVaccinationHistoryFragment() {
        super();
    }

    public static GridVaccinationHistoryFragment newInstance(GridVaccinationHistoryCriteriaObject criteria) {
        GridVaccinationHistoryFragment fragment = new GridVaccinationHistoryFragment();
        fragment.criteria = criteria;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public GridVaccinationHistoryFragmentInterface.Presenter createPresenter() {
        return GridVaccinationHistoryFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_grid_vaccination_history;
    }

    @Override
    public void bindView(View view) {
        mainView = (LinearLayout) view.findViewById(R.id.mainView);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();
    }

    @Override
    public void initialize() {
        if (criteria != null && criteria.getIsCanLoad()) {
            searchVaccinationHistory(criteria);
        }
    }

    public void searchVaccinationHistory(GridVaccinationHistoryCriteriaObject criteria) {
        getPresenter().getGridDataVaccinationHistory(criteria);
    }

    @Override
    public void setGridDataVaccinationHistory(GridVaccinationHistoryObject data) {
        if (data != null) {
            this.data = data;
            this.setGridVaccinationHistory();
        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle(getResources().getString(R.string.dialog_default_title))
                    .setMessage(getResources().getString(R.string.data_not_found))
                    .setPositiveButton(getResources().getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    }).show();
        }

        this.onLoadListener.onLoadComplete();
    }

    private void setGridVaccinationHistory() {
        RelativeLayout.LayoutParams gridParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        GridMatrixLayout grid = new GridMatrixLayout(getActivity());
        grid.setHeaderRowStyle(R.style.ehr_TextStyle_Title_Green);
        grid.setHeaderColumnStyle(R.style.ehr_TextStyle_SubTitle_Green);
        grid.setGridColor(R.color.color_green);
        int px120 = (int) CommonUtils.convertDpToPixel(getActivity(), 40);
        int px200 = (int) CommonUtils.convertDpToPixel(getActivity(), 67);
        int px250 = (int) CommonUtils.convertDpToPixel(getActivity(), 84);
        int px9 = (int) CommonUtils.convertDpToPixel(getActivity(), 3);
        grid.setPaddingItem(px9, px9, px9, px9);
        grid.setItemImage(px120, px120, R.drawable.img_child_active, R.drawable.img_child_inactive);
        grid.initialize(px200, px250, this.data.getColumnHeaderListTemp(), this.data.getRowHeaderList(), this.data.getRowDataList(), AppConstants.GridMatrixType.Image);
        grid.setOnItemClickListener(this.onGridItemClick());
        grid.setOnRowHeaderClickListener(this.onGridRowClick());

        this.mainView.removeAllViews();
        this.mainView.addView(grid, gridParams);
    }

    public GridMatrixLayout.OnItemClickListener onGridItemClick() {
        return new GridMatrixLayout.OnItemClickListener<VaccinationHistoryObject>() {
            @Override
            public void onItemClick(VaccinationHistoryObject data) {
                if (!data.getActive()) {
                    getPresenter().activeDataVaccinationHistory(data, criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getMenuCode());
                } else {
                    getPresenter().inActiveDataVaccinationHistory(data.getPatientVaccinationId());
                }
            }
        };
    }

    @Override
    public void onActiveSuccess(VaccinationHistoryObject data) {
        this.onLoadListener.onActiveComplete(data.getPatientMenuId());
    }

    @Override
    public void onInActiveSuccess() {
        this.onLoadListener.onInActiveComplete();
    }

    public GridMatrixLayout.OnRowHeaderClickListener onGridRowClick() {
        return new GridMatrixLayout.OnRowHeaderClickListener() {
            @Override
            public void onRowHeaderClick(int position) {
                RowHeaderVaccineObject vc = data.getRowHeaderList().get(position);
                ((MainActivity) getActivity()).openVaccineDetail(vc);
            }
        };
    }


}
