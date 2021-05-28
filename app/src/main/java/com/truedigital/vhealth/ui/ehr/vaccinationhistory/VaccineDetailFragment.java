package com.truedigital.vhealth.ui.ehr.vaccinationhistory;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.RowHeaderVaccineObject;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;

public class VaccineDetailFragment extends BaseMvpFragment<VaccineDetailFragmentInterface.Presenter>
        implements VaccineDetailFragmentInterface.View {

    private RowHeaderVaccineObject data;

    private WebView webviewVaccineDetail;

    public VaccineDetailFragment() {
        super();
    }

    public static VaccineDetailFragment newInstance(RowHeaderVaccineObject data) {
        Bundle args = new Bundle();
        VaccineDetailFragment fragment = new VaccineDetailFragment();
        fragment.data = data;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public VaccineDetailFragmentInterface.Presenter createPresenter() {
        return VaccineDetailFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_vaccine_detail;
    }

    @Override
    public void bindView(View view) {
        webviewVaccineDetail = (WebView) view.findViewById(R.id.webviewVaccineDetail);
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
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.vaccination_detail), false,null, false);

        webviewVaccineDetail.loadDataWithBaseURL(null, data.getDetail(), "text/html", "UTF-8", "");
        webviewVaccineDetail.setBackgroundColor(Color.TRANSPARENT);
        webviewVaccineDetail.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
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


}
