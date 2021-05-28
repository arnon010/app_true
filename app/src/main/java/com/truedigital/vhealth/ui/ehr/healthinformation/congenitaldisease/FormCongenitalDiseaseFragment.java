package com.truedigital.vhealth.ui.ehr.healthinformation.congenitaldisease;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.CongenitalDiseaseImageObject;
import com.truedigital.vhealth.model.CongenitalDiseaseObject;
import com.truedigital.vhealth.model.MediaObject;
import com.truedigital.vhealth.model.SystemConfigurationObject;
import com.truedigital.vhealth.ui.adapter.ListImageAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.dialog.MediaBottomSheetFragment;
import com.truedigital.vhealth.ui.dialog.ShowImageDialog;
import com.truedigital.vhealth.ui.dialog.SuccessDialog;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.Camera;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.FileUtils;
import com.truedigital.vhealth.utils.PickFile;
import com.truedigital.vhealth.utils.SpacesItemDecoration;
import com.truedigital.vhealth.utils.StringUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class FormCongenitalDiseaseFragment extends BaseMvpFragment<FormCongenitalDiseaseFragmentInterface.Presenter>
        implements FormCongenitalDiseaseFragmentInterface.View {

    private CongenitalDiseaseObject data;
    private boolean isNew;
    private boolean isNewFromMenu;

    private EditText edtTitle;
    private EditText edtDescription;
    private RecyclerView rvImage;
    private Button btnSave;
    private TextView btnCancel;

    private ListImageAdapter listImageAdapter;

    private Fragment fg;

    public FormCongenitalDiseaseFragment() {
        super();
    }

    public static FormCongenitalDiseaseFragment newInstance(CongenitalDiseaseObject data, boolean isNew, boolean isNewFromMenu) {
        FormCongenitalDiseaseFragment fragment = new FormCongenitalDiseaseFragment();
        fragment.data = data;
        fragment.isNew = isNew;
        fragment.isNewFromMenu = isNewFromMenu;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public FormCongenitalDiseaseFragmentInterface.Presenter createPresenter() {
        return FormCongenitalDiseaseFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_form_congenital_disease;
    }

    @Override
    public void bindView(View view) {
        edtTitle = (EditText) view.findViewById(R.id.edtTitle);
        edtDescription = (EditText) view.findViewById(R.id.edtDescription);
        rvImage = (RecyclerView) view.findViewById(R.id.recycler_image);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnCancel = (TextView) view.findViewById(R.id.btnCancel);
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
        this.fg = this;

        if (!this.isNew) {
            ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.update_congenital_disease), false,null, false);

            edtTitle.setText(data.getTitle());
            edtDescription.setText(data.getDetail());
            btnSave.setText(getResources().getString(R.string.update_congenital_disease));
            btnSave.setOnClickListener(this.onUpdateClickListener());
        } else {
            ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.new_congenital_disease), false,null, false);

            btnSave.setText(getResources().getString(R.string.new_congenital_disease));
            btnSave.setOnClickListener(this.onNewClickListener());
        }

        StringUtils.setUnderline(btnCancel);
        btnCancel.setOnClickListener(this.onCancelClickListener());

        data.getAttachmentList().add(new CongenitalDiseaseImageObject());
        setTempImageList();

        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequire();
            }
        });

        checkRequire();
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

    private void setTempImageList() {

        ArrayList<CongenitalDiseaseImageObject> temp = new ArrayList<>();
        for (CongenitalDiseaseImageObject img : data.getAttachmentList()) {
            if (img.getIsDelete() == null || !img.getIsDelete()) {
                temp.add(img);
            }
        }

        listImageAdapter.setListData(temp);
    }

    private ListImageAdapter.OnClickListener<CongenitalDiseaseImageObject> onImageListClickListener() {
        return new ListImageAdapter.OnClickListener<CongenitalDiseaseImageObject>() {
            @Override
            public void onAddClick() {

                final MediaBottomSheetFragment mediaBottomSheet = MediaBottomSheetFragment.newInstance((int) CommonUtils.convertDpToPixel(getActivity(), 200));
                mediaBottomSheet.show(getActivity().getSupportFragmentManager(), mediaBottomSheet.getTag());
                mediaBottomSheet.setOnClickListener(new MediaBottomSheetFragment.OnClickListener() {
                    @Override
                    public void onSelect(MediaObject data) {
                        if (data.getMediaId() == AppConstants.REQUEST_PICK_IMAGE) {
                            mediaBottomSheet.dismiss();

                            PickFile.pickImageIntentWithPermission(getActivity(), fg, AppConstants.REQUEST_PICK_IMAGE);

                        } else if (data.getMediaId() == AppConstants.REQUEST_CAMERA) {
                            mediaBottomSheet.dismiss();

                            Camera.takePhotosIntentWithPermission(getActivity(), fg, AppConstants.REQUEST_CAMERA);
                        }
                    }
                });
            }

            @Override
            public void onViewClick(CongenitalDiseaseImageObject data) {
                if (!data.getIsVideo()) {
                    ShowImageDialog fullImageDialog = new ShowImageDialog(getActivity(), data.getImageUrl());
                    fullImageDialog.showDialog();
                }
            }

            @Override
            public void onDelete(final CongenitalDiseaseImageObject img) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.dialog_confirm_delete_picture))
                        .setPositiveButton(getResources().getString(R.string.dialog_need_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                int index = data.getAttachmentList().indexOf(img);
                                if (index >= 0 && img.getIsNew() != null && !img.getIsNew()) {

                                    data.getAttachmentList().get(index).setIsDelete(true);
                                    setTempImageList();

                                } else if (index >= 0 && img.getIsNew() != null && img.getIsNew()) {

                                    data.getAttachmentList().remove(img);
                                    setTempImageList();
                                }
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.dialog_un_need_button), null).show();
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstants.REQUEST_PICK_IMAGE_IMAGE_WRITE_EXTERNAL_STORAGE:
            case AppConstants.REQUEST_PICK_IMAGE_IMAGE_READ_EXTERNAL_STORAGE:
                PickFile.pickImageIntentWithPermission(getActivity(), fg, AppConstants.REQUEST_PICK_IMAGE);
                break;
            case AppConstants.REQUEST_CAMERA_READ_WRITE_EXTERNAL_STORAGE:
            case AppConstants.REQUEST_CAMERA_READ_READ_EXTERNAL_STORAGE:
            case AppConstants.REQUEST_CAMERA:
                Camera.takePhotosIntentWithPermission(getActivity(), fg, AppConstants.REQUEST_CAMERA);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        if (resultCode == RESULT_OK) {

            Uri uriImage = null;
            if (requestCode == AppConstants.REQUEST_PICK_IMAGE && dataIntent != null && dataIntent.getData() != null) {

                uriImage = dataIntent.getData();
            } else if (requestCode == AppConstants.REQUEST_CAMERA) {

                uriImage = Camera.moveFile(getContext());
            }

            CongenitalDiseaseImageObject imgNew = new CongenitalDiseaseImageObject();
            imgNew.setIsNew(true);
            imgNew.setImageUrl(FileUtils.getRealPathFromURI(getActivity(), uriImage));
            imgNew.setImageUri(uriImage);

            ArrayList<String> messages = new ArrayList();
            SystemConfigurationObject config = ((MainActivity) getActivity()).getSystemConfiguration();

            ContentResolver cr = getContext().getContentResolver();
            String mimeType = cr.getType(uriImage);


            if (mimeType != null && mimeType.startsWith("video")) {
                if (FileUtils.fileZize(getActivity(), uriImage) > config.getVideoMaxSizeByte()) {
                    messages.add(String.format(getResources().getString(R.string.file_size_must_not_greater_than_mb), (float) config.getVideoMaxSizeByte() / 1048576));
                }
                imgNew.setIsVideo(true);

            } else if (mimeType != null && mimeType.startsWith("image")) {
                if (FileUtils.fileZize(getActivity(), uriImage) > config.getImageMaxSizeByte()) {
                    messages.add(String.format(getResources().getString(R.string.file_size_must_not_greater_than_mb), (float) config.getImageMaxSizeByte() / 1048576));
                }
                imgNew.setIsVideo(false);
            }

            if (messages.size() == 0) {
                this.data.getAttachmentList().add(this.data.getAttachmentList().size() - 1, imgNew);
                setTempImageList();
            } else {
                getPresenter().getView().showMessage(StringUtils.getMessageNewline(messages));
            }
        }
    }

    private void setData() {
        data.setTitle(edtTitle.getText().toString());
        data.setDetail(edtDescription.getText().toString());
    }

    private void checkRequire() {
        if (edtTitle.getText().toString() == null
                || edtTitle.getText().toString().equals("")) {
            setEnableDisableBtn(false);
        } else {
            setEnableDisableBtn(true);
        }
    }

    private void setEnableDisableBtn(boolean enable) {
        if (enable) {
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }


    private View.OnClickListener onNewClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                getPresenter().newCongenitalDisease(getActivity(), data);
            }
        };
    }

    private View.OnClickListener onUpdateClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                getPresenter().updateCongenitalDisease(getActivity(), data);
            }
        };
    }

    private View.OnClickListener onCancelClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        };
    }

    @Override
    public void onNewSuccess(final CongenitalDiseaseObject data) {
        SuccessDialog dialog = new SuccessDialog(getActivity());
        dialog.showDialog(getResources().getString(R.string.new_congenital_disease_success));
        dialog.setOnShowFinishListener(new SuccessDialog.OnShowFinishListener() {
            @Override
            public void onShowFinish() {
                MainActivity main = ((MainActivity) getActivity());
                if (isNewFromMenu) {
                    main.removeToFragment(FormCongenitalDiseaseFragment.class.toString());
                    main.openCongenitalDisease(data.getPatientId(), data.getPatientMenuId());
                } else {
                    int index = getActivity().getSupportFragmentManager().getBackStackEntryCount() - 2;
                    if (index >= 0) {
                        FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                        String tag = backEntry.getName();
                        CongenitalDiseaseFragment fragment = (CongenitalDiseaseFragment) getFragmentManager().findFragmentByTag(tag);
                        fragment.clearTempList();
                    }

                    main.onBackPressed();
                }
            }
        });
    }

    @Override
    public void onUpdateSuccess(CongenitalDiseaseObject data) {
        SuccessDialog dialog = new SuccessDialog(getActivity());
        dialog.showDialog(getResources().getString(R.string.update_congenital_disease_success));
        dialog.setOnShowFinishListener(new SuccessDialog.OnShowFinishListener() {
            @Override
            public void onShowFinish() {
                int index = getActivity().getSupportFragmentManager().getBackStackEntryCount() - 2;
                if (index >= 0) {
                    FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                    String tag = backEntry.getName();
                    CongenitalDiseaseFragment fragment = (CongenitalDiseaseFragment) getFragmentManager().findFragmentByTag(tag);
                    fragment.clearTempList();
                }

                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }

}
