package com.truedigital.vhealth.ui.ehr.laboratory;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.GridLaboratoryChiiwiiCriteriaObject;
import com.truedigital.vhealth.model.GridLaboratoryChiiwiiObject;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.GridMatrixLayout;

public class GridLaboratoryChiiwiiFragment extends BaseMvpFragment<GridLaboratoryChiiwiiFragmentInterface.Presenter>
        implements GridLaboratoryChiiwiiFragmentInterface.View {

    private LinearLayout mainView;

    private GridLaboratoryChiiwiiCriteriaObject criteria;
    private GridLaboratoryChiiwiiObject data;
    private GridLaboratoryChiiwiiFragment.OnLoadListener onLoadListener;

    public interface OnLoadListener {
        void onLoadComplete();
    }

    public void setOnLoadListener(GridLaboratoryChiiwiiFragment.OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public GridLaboratoryChiiwiiFragment() {
        super();
    }

    public static GridLaboratoryChiiwiiFragment newInstance(GridLaboratoryChiiwiiCriteriaObject criteria) {
        GridLaboratoryChiiwiiFragment fragment = new GridLaboratoryChiiwiiFragment();
        fragment.criteria = criteria;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public GridLaboratoryChiiwiiFragmentInterface.Presenter createPresenter() {
        return GridLaboratoryChiiwiiFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_grid_laboratory_chiiwii;
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
            searchLaboratoryChiiwii(criteria);
        }
    }

    public void searchLaboratoryChiiwii(GridLaboratoryChiiwiiCriteriaObject criteria) {
        getPresenter().getGridDataLaboratoryChiiwii(criteria);
    }

    @Override
    public void setGridDataLaboratoryChiiwii(GridLaboratoryChiiwiiObject data) {
        if (data != null) {
            this.data = data;
            this.setGridLaboratoryChiiwii();
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

    private void setGridLaboratoryChiiwii() {
        RelativeLayout.LayoutParams gridParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        GridMatrixLayout grid = new GridMatrixLayout(getActivity());
        grid.setHeaderRowStyle(R.style.ehr_TextStyle_Title_Green);
        grid.setHeaderColumnStyle(R.style.ehr_TextStyle_SubTitle_Green);
        grid.setBodyStyle(R.style.ehr_TextStyle_Title_Sub);
        grid.setGridColor(R.color.color_green);
        int px12 = (int) CommonUtils.convertDpToPixel(getActivity(), 4);
        int px300 = (int) CommonUtils.convertDpToPixel(getActivity(), 100);
        int px350 = (int) CommonUtils.convertDpToPixel(getActivity(), 117);
        grid.setMarginsRow(0, px12, 0, px12);
        grid.setPaddingItem(px12, px12, px12, px12);
        grid.initialize(px300, px350, this.data.getColumnHeaderListTemp(), this.data.getRowHeaderList(), this.data.getRowDataList(), AppConstants.GridMatrixType.Text);

        this.mainView.removeAllViews();
        this.mainView.addView(grid, gridParams);
    }

}
