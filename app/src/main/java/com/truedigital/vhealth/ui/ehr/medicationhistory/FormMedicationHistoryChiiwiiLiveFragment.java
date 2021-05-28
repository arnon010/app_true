package com.truedigital.vhealth.ui.ehr.medicationhistory;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.MedicationHistoryObject;
import com.truedigital.vhealth.model.MedicationHistoryImageObject;
import com.truedigital.vhealth.ui.adapter.ListImageAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.dialog.ShowImageDialog;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.SpacesItemDecoration;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class FormMedicationHistoryChiiwiiLiveFragment extends BaseMvpFragment<FormMedicationHistoryChiiwiiLiveFragmentInterface.Presenter>
        implements FormMedicationHistoryChiiwiiLiveFragmentInterface.View {

    private MedicationHistoryObject data;

    private EditText edtDrugName;
    private EditText edtUnit;
    private EditText edtHowTo;
    private EditText edtFromName;
    private RecyclerView rvImage;
    private Button btnBack;

    private ListImageAdapter listImageAdapter;

    public FormMedicationHistoryChiiwiiLiveFragment() {
        super();
    }

    public static FormMedicationHistoryChiiwiiLiveFragment newInstance(MedicationHistoryObject data) {
        FormMedicationHistoryChiiwiiLiveFragment fragment = new FormMedicationHistoryChiiwiiLiveFragment();
        fragment.data = data;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public FormMedicationHistoryChiiwiiLiveFragmentInterface.Presenter createPresenter() {
        return FormMedicationHistoryChiiwiiLiveFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_form_medication_history_chiiwiilive;
    }

    @Override
    public void bindView(View view) {
        edtDrugName = (EditText) view.findViewById(R.id.edtDrugName);
        edtUnit = (EditText) view.findViewById(R.id.edtUnit);
        edtHowTo = (EditText) view.findViewById(R.id.edtHowTo);
        edtFromName = (EditText) view.findViewById(R.id.edtFromName);
        rvImage = (RecyclerView) view.findViewById(R.id.recycler_image);
        btnBack = (Button) view.findViewById(R.id.btnBack);
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
        setAdapter();
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.medication_from_chiiwii_live), false,null, false);

        edtDrugName.setText(data.getTitle());

        String unit = "";
        if (data.getQuantity() > 0 && !data.getUnit().equals("")) {
            unit = data.getQuantity() + "/" + data.getUnit();
        } else if (data.getQuantity() > 0) {
            unit = data.getQuantity() + "";
        }
        edtUnit.setText(unit);

        edtHowTo.setText(data.getHowToUse());
        edtFromName.setText(data.getPlace());
        listImageAdapter.setListData(data.getAttachmentList());
        btnBack.setOnClickListener(this.onBackClickListener());
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

    private void setAdapter() {
        FlexboxLayoutManager flexboxManager = new FlexboxLayoutManager(getActivity());
        flexboxManager.setFlexWrap(FlexWrap.WRAP);
        flexboxManager.setFlexDirection(FlexDirection.ROW);
        flexboxManager.setJustifyContent(JustifyContent.FLEX_START);
        rvImage.setLayoutManager(flexboxManager);
        rvImage.addItemDecoration(new SpacesItemDecoration((int) CommonUtils.convertDpToPixel(getActivity(), 3)));
        int px300 = (int) CommonUtils.convertDpToPixel(getActivity(), 100);
        listImageAdapter = new ListImageAdapter(getActivity(), this, px300, px300);
        listImageAdapter.setOnClickListener(this.onImageListClickListener());
        rvImage.setAdapter(listImageAdapter);
    }

    private ListImageAdapter.OnClickListener<MedicationHistoryImageObject> onImageListClickListener() {
        return new ListImageAdapter.OnClickListener<MedicationHistoryImageObject>() {
            @Override
            public void onAddClick() {
            }

            @Override
            public void onViewClick(MedicationHistoryImageObject data) {
                if (!data.getIsVideo()) {
                    ShowImageDialog fullImageDialog = new ShowImageDialog(getActivity(), data.getImageUrl());
                    fullImageDialog.showDialog();
                }
            }

            @Override
            public void onDelete(MedicationHistoryImageObject data) {

            }
        };
    }

    private View.OnClickListener onBackClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        };
    }

}
