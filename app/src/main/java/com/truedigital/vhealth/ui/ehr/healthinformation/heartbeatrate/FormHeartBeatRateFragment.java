package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.HeartBeatRateObject;
import com.truedigital.vhealth.model.HeartBeatRateTypeObject;
import com.truedigital.vhealth.ui.adapter.ListBottomSheetAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.dialog.CalendarBottomSheetFragment;
import com.truedigital.vhealth.ui.dialog.ListBottomSheetFragment;
import com.truedigital.vhealth.ui.dialog.SuccessDialog;
import com.truedigital.vhealth.ui.dialog.TimePickerBottomSheetFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FormHeartBeatRateFragment extends BaseMvpFragment<FormHeartBeatRateFragmentInterface.Presenter>
        implements FormHeartBeatRateFragmentInterface.View {

    private RelativeLayout relativeType;
    private RelativeLayout relativeDate;
    private RelativeLayout relativeTime;
    private RelativeLayout relativeBPM;
    private TextView txtType;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtBPM;
    private EditText edtBPM;
    private Button btnSave;
    private TextView btnDelete;

    private boolean isOpenEdtBPM = false;
    private boolean isNew;
    private HeartBeatRateObject data;
    private ArrayList<HeartBeatRateTypeObject> typeList;

    public FormHeartBeatRateFragment() {
        super();
    }

    public static FormHeartBeatRateFragment newInstance(HeartBeatRateObject data, boolean isNew) {
        FormHeartBeatRateFragment fragment = new FormHeartBeatRateFragment();
        fragment.isNew = isNew;
        fragment.data = data;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public FormHeartBeatRateFragmentInterface.Presenter createPresenter() {
        return FormHeartBeatRateFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_form_heart_beat_rate;
    }

    @Override
    public void bindView(View view) {
        relativeType = (RelativeLayout) view.findViewById(R.id.relativeType);
        relativeDate = (RelativeLayout) view.findViewById(R.id.relativeDate);
        relativeTime = (RelativeLayout) view.findViewById(R.id.relativeTime);
        relativeBPM = (RelativeLayout) view.findViewById(R.id.relativeBPM);
        txtType = (TextView) view.findViewById(R.id.txtType);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        txtBPM = (TextView) view.findViewById(R.id.txtBPM);
        edtBPM = (EditText) view.findViewById(R.id.edtBPM);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnDelete = (TextView) view.findViewById(R.id.btnDelete);
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
        getPresenter().getTypeList();

        relativeType.setOnClickListener(selectTypeClickListener());

        if (!this.isNew) {
            ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.update_heart_beat_rate), false,null, false);

            txtDate.setText(ConvertDate.convertDateSimpleShow(data.getRecordDate()));
            txtTime.setText(ConvertDate.convertTimeToString(data.getRecordDate()));
            edtBPM.setText(String.valueOf(data.getBPM()));
            txtBPM.setText(String.valueOf(data.getBPM()));

            btnSave.setText(getResources().getString(R.string.update_heart_beat_rate));
            btnSave.setOnClickListener(this.onUpdateClickListener());
            btnDelete.setText(getResources().getString(R.string.delete));
            btnDelete.setOnClickListener(this.onDeleteClickListener());

        } else {
            ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.new_heart_beat_rate), false,null, false);

            relativeDate.setOnClickListener(selectDateClickListener());
            relativeTime.setOnClickListener(selectTimeListener());

            btnSave.setText(getResources().getString(R.string.new_heart_beat_rate));
            btnSave.setOnClickListener(this.onNewClickListener());
            btnDelete.setText(getResources().getString(R.string.button_cancel));
            btnDelete.setOnClickListener(this.onCancelClickListener());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            data.setRecordDate(calendar.getTime());
            txtDate.setText(ConvertDate.convertDateSimpleShow(data.getRecordDate()));
            txtTime.setText(ConvertDate.convertTimeToString(data.getRecordDate()));
        }

        StringUtils.setUnderline(btnDelete);

        relativeBPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpenEdtBPM) {
                    isOpenEdtBPM = true;
                    txtBPM.setVisibility(View.GONE);
                    edtBPM.setVisibility(View.VISIBLE);
                    edtBPM.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edtBPM, InputMethodManager.SHOW_IMPLICIT);
                    edtBPM.setSelection(edtBPM.getText().length());
                }
            }
        });

        edtBPM.addTextChangedListener(new TextWatcher() {
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

    private View.OnClickListener selectTypeClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListBottomSheetAdapter adapter = new ListBottomSheetAdapter(getActivity());
                adapter.setListData(typeList);

                final ListBottomSheetFragment selectTypeBottomSheet = ListBottomSheetFragment.newInstance(R.layout.fragment_list_bottom_sheet, (int) CommonUtils.convertDpToPixel(getActivity(), 200), adapter);
                selectTypeBottomSheet.show(getActivity().getSupportFragmentManager(), selectTypeBottomSheet.getTag());

                adapter.setOnClickListener(new ListBottomSheetAdapter.OnItemClickListener<BottomSheetObject>() {
                    @Override
                    public void onItemClick(BottomSheetObject type) {
                        selectTypeBottomSheet.dismiss();
                        data.setType(type.getValueItem());
                        txtType.setText(type.getDescriptions());
                        checkRequire();
                    }
                });

            }
        };
    }

    private View.OnClickListener selectDateClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CalendarBottomSheetFragment selectDateBottomSheet = CalendarBottomSheetFragment.newInstance(getActivity(), (int) CommonUtils.convertDpToPixel(getActivity(), 370), data.getRecordDate());
                selectDateBottomSheet.show(getActivity().getSupportFragmentManager(), selectDateBottomSheet.getTag());
                selectDateBottomSheet.setOnDateChangeListener(new CalendarBottomSheetFragment.OnDateChangeListener() {
                    @Override
                    public void onDateChange(Date date) {
                        selectDateBottomSheet.dismiss();

                        int hours = 0;
                        int minutes = 0;
                        if (data.getRecordDate() != null) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(data.getRecordDate());
                            hours = calendar.get(Calendar.HOUR_OF_DAY);
                            minutes = calendar.get(Calendar.MINUTE);
                        }

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.set(Calendar.HOUR_OF_DAY, hours);
                        calendar.set(Calendar.MINUTE, minutes);
                        calendar.set(Calendar.SECOND, 0);

                        data.setRecordDate(calendar.getTime());
                        txtDate.setText(ConvertDate.convertDateSimpleShow(date));
                        txtTime.setText(ConvertDate.convertTimeToString(data.getRecordDate()));
                    }
                });
            }
        };
    }

    private View.OnClickListener selectTimeListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hours = -1;
                int minutes = -1;
                if (data.getRecordDate() != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(data.getRecordDate());
                    hours = calendar.get(Calendar.HOUR_OF_DAY);
                    minutes = calendar.get(Calendar.MINUTE);
                }

                final TimePickerBottomSheetFragment selectTimeBottomSheet = TimePickerBottomSheetFragment.newInstance(getActivity(), (int) CommonUtils.convertDpToPixel(getActivity(), 200), hours, minutes);
                selectTimeBottomSheet.show(getActivity().getSupportFragmentManager(), selectTimeBottomSheet.getTag());
                selectTimeBottomSheet.setOnChangeListener(new TimePickerBottomSheetFragment.OnChangeListener() {
                    @Override
                    public void onTimeChange(int hourOfDay, int minute) {
                        if (data.getRecordDate() == null) {
                            data.setRecordDate(new Date());
                        }

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(data.getRecordDate());
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        data.setRecordDate(calendar.getTime());
                        txtDate.setText(ConvertDate.convertDateSimpleShow(data.getRecordDate()));
                        txtTime.setText(ConvertDate.convertTimeToString(data.getRecordDate()));
                    }
                });
            }
        };
    }

    @Override
    public void setTypeList(ArrayList<HeartBeatRateTypeObject> data) {
        this.typeList = data;
        if (this.data.getType() != null) {
            for (BottomSheetObject type : typeList) {
                if (this.data.getType().equals(type.getValueItem())) {
                    txtType.setText(type.getDescriptions());
                    break;
                }
            }
        }
    }

    private View.OnClickListener onNewClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupObject();
                getPresenter().newHeartBeatRate(data);
            }
        };
    }

    private View.OnClickListener onUpdateClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupObject();
                getPresenter().updateHeartBeatRate(data);
            }
        };
    }

    private void setupObject() {
        data.setBPM(Integer.parseInt(edtBPM.getText().toString()));
    }

    private void checkRequire() {
        if (data.getType() == null
                || data.getType().equals("")
                || data.getRecordDate() == null
                || edtBPM.getText().toString() == null
                || edtBPM.getText().toString().equals("")) {
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

    private View.OnClickListener onDeleteClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.dialog_confirm_delete))
                        .setPositiveButton(getResources().getString(R.string.dialog_need_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getPresenter().deleteHeartBeatRate(data);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.dialog_un_need_button), null).show();
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
    public void onNewSuccess(final HeartBeatRateObject data) {
        SuccessDialog dialog = new SuccessDialog(getActivity());
        dialog.showDialog(getResources().getString(R.string.new_heart_beat_rate_success));
        dialog.setOnShowFinishListener(new SuccessDialog.OnShowFinishListener() {
            @Override
            public void onShowFinish() {
                int index = getActivity().getSupportFragmentManager().getBackStackEntryCount() - 2;
                if (index >= 0) {
                    FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                    String tag = backEntry.getName();
                    HeartBeatRateFragment fragment = (HeartBeatRateFragment) getFragmentManager().findFragmentByTag(tag);
                    fragment.setPatientMenuId(data.getPatientMenuId());
                }

                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }

    @Override
    public void onUpdateSuccess() {
        SuccessDialog dialog = new SuccessDialog(getActivity());
        dialog.showDialog(getResources().getString(R.string.update_heart_beat_rate_success));
        dialog.setOnShowFinishListener(new SuccessDialog.OnShowFinishListener() {
            @Override
            public void onShowFinish() {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }

    @Override
    public void onDeleteSuccess() {
        SuccessDialog dialog = new SuccessDialog(getActivity());
        dialog.showDialog(getResources().getString(R.string.delete_heart_beat_rate_success));
        dialog.setOnShowFinishListener(new SuccessDialog.OnShowFinishListener() {
            @Override
            public void onShowFinish() {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }


}
