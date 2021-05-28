package com.truedigital.vhealth.ui.ehr.healthinformation.childgrowth;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ChildGrowthCriteriaObject;
import com.truedigital.vhealth.model.ChildGrowthHistoryObject;
import com.truedigital.vhealth.model.GridChildGrowthHistoryObject;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.GridMatrixLayout;

public class ChildGrowthFragment extends BaseMvpFragment<ChildGrowthFragmentInterface.Presenter>
        implements ChildGrowthFragmentInterface.View {

    private LinearLayout mainView;

    private ChildGrowthCriteriaObject criteria;
    private GridChildGrowthHistoryObject data;

    public ChildGrowthFragment() {
        super();
    }

    public static ChildGrowthFragment newInstance(ChildGrowthCriteriaObject criteria) {
        ChildGrowthFragment fragment = new ChildGrowthFragment();
        fragment.criteria = criteria;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ChildGrowthFragmentInterface.Presenter createPresenter() {
        return ChildGrowthFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_child_growth;
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
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.child_growth), false, null, false);

        if (criteria != null) {
            this.searchChildGrowthHistory();
        }
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

    public void searchChildGrowthHistory() {
        getPresenter().getGridDataChildGrowthHistory(criteria);
    }

    @Override
    public void setGridDataChildGrowthHistory(GridChildGrowthHistoryObject data) {
        if (data != null) {
            this.data = data;
            this.setGridChildGrowthHistory();
        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle(getResources().getString(R.string.dialog_default_title))
                    .setMessage(getResources().getString(R.string.data_not_found))
                    .setPositiveButton(getResources().getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    }).show();
        }
    }

    private void setGridChildGrowthHistory() {
        RelativeLayout.LayoutParams gridParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        GridMatrixLayout grid = new GridMatrixLayout(getActivity());
        grid.setHeaderRowStyle(R.style.ehr_TextStyle_Title_Green);
        grid.setHeaderColumnStyle(R.style.ehr_TextStyle_SubTitle_Green);
        grid.setBodyStyle(R.style.ehr_TextStyle_Title_Sub);
        grid.setGridColor(R.color.color_green);
        grid.setRowHeaderTextGravity(Gravity.CENTER);
        int px40 = (int) CommonUtils.convertDpToPixel(getActivity(), 13);
        int px20 = (int) CommonUtils.convertDpToPixel(getActivity(), 7);
        int px200 = (int) CommonUtils.convertDpToPixel(getActivity(), 67);
        int px400 = (int) CommonUtils.convertDpToPixel(getActivity(), 134);
        int px250 = (int) CommonUtils.convertDpToPixel(getActivity(), 83);
        grid.setPaddingItem(px40, px20, px40, px20);
        grid.setItemImage(px200, px200, R.drawable.img_child_active_fade, R.drawable.img_child_inactive_fade);
        grid.initialize(px400, px250, this.data.getColumnHeaderListTemp(), this.data.getRowHeaderList(), this.data.getRowDataList(), AppConstants.GridMatrixType.TextAndImage);
        grid.setOnItemClickListener(this.onGridItemClick());

        this.mainView.removeAllViews();
        this.mainView.addView(grid, gridParams);
    }

    public GridMatrixLayout.OnItemClickListener onGridItemClick() {
        return new GridMatrixLayout.OnItemClickListener<ChildGrowthHistoryObject>() {
            @Override
            public void onItemClick(ChildGrowthHistoryObject data) {
                if (!data.getActive()) {
                    getPresenter().activeDataChildGrowthHistory(data, criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getMenuCode());
                } else {
                    getPresenter().inActiveDataChildGrowthHistory(data.getPatientChildGrowthId());
                }
            }
        };
    }

    @Override
    public void onActiveSuccess(ChildGrowthHistoryObject data) {
        criteria.setPatientMenuId(data.getPatientMenuId());
        this.searchChildGrowthHistory();
    }

    @Override
    public void onInActiveSuccess() {
        this.searchChildGrowthHistory();
    }


}
