package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

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
import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.dialog.CalendarBottomSheetFragment;
import com.truedigital.vhealth.ui.dialog.SuccessDialog;
import com.truedigital.vhealth.ui.dialog.TimePickerBottomSheetFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;

public class FormBloodSugarFragment extends BaseMvpFragment<FormBloodSugarFragmentInterface.Presenter>
        implements FormBloodSugarFragmentInterface.View {

    private RelativeLayout relativeDate;
    private RelativeLayout relativeTime;
    private RelativeLayout relativeBloodGlucose;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtBloodGlucose;
    private EditText edtBloodGlucose;
    private Button btnSave;
    private TextView btnDelete;

    private boolean isOpenEdtBloodGlucose = false;
    private boolean isNew;
    private BloodSugarObject data;

    public FormBloodSugarFragment() {
        super();
    }

    public static FormBloodSugarFragment newInstance(BloodSugarObject data, boolean isNew) {
        FormBloodSugarFragment fragment = new FormBloodSugarFragment();
        fragment.isNew = isNew;
        fragment.data = data;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public FormBloodSugarFragmentInterface.Presenter createPresenter() {
        return FormBloodSugarFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_form_blood_sugar;
    }

    @Override
    public void bindView(View view) {
        relativeDate = (RelativeLayout) view.findViewById(R.id.relativeDate);
        relativeTime = (RelativeLayout) view.findViewById(R.id.relativeTime);
        relativeBloodGlucose = (RelativeLayout) view.findViewById(R.id.relativeBloodGlucose);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        txtBloodGlucose = (TextView) view.findViewById(R.id.txtBloodGlucose);
        edtBloodGlucose = (EditText) view.findViewById(R.id.edtBloodGlucose);
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

        if (!this.isNew) {
            ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.update_blood_sugar), false,null, false);

            txtDate.setText(ConvertDate.convertDateSimpleShow(data.getRecordDate()));
            txtTime.setText(ConvertDate.convertTimeToString(data.getRecordDate()));
            txtBloodGlucose.setText(String.valueOf(data.getSugarValue()));
            edtBloodGlucose.setText(String.valueOf(data.getSugarValue()));

            btnSave.setText(getResources().getString(R.string.update_blood_sugar));
            btnSave.setOnClickListener(this.onUpdateClickListener());
            btnDelete.setText(getResources().getString(R.string.delete));
            btnDelete.setOnClickListener(this.onDeleteClickListener());

        } else {
            ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.new_blood_sugar), false,null, false);

            relativeDate.setOnClickListener(selectDateClickListener());
            relativeTime.setOnClickListener(selectTimeListener());

            btnSave.setText(getResources().getString(R.string.new_blood_sugar));
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

        relativeBloodGlucose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpenEdtBloodGlucose) {
                    isOpenEdtBloodGlucose = true;
                    txtBloodGlucose.setVisibility(View.GONE);
                    edtBloodGlucose.setVisibility(View.VISIBLE);
                    edtBloodGlucose.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edtBloodGlucose, InputMethodManager.SHOW_IMPLICIT);
                    edtBloodGlucose.setSelection(edtBloodGlucose.getText().length());
                }
            }
        });

        edtBloodGlucose.addTextChangedListener(new TextWatcher() {
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

                        Date heartBeatRateDate = data.getRecordDate();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(heartBeatRateDate);
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

    private View.OnClickListener onNewClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupObject();
                getPresenter().newBloodSugar(data);
            }
        };
    }

    private View.OnClickListener onUpdateClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupObject();
                getPresenter().updateBloodSugar(data);
            }
        };
    }

    private void setupObject() {
        data.setSugarValue(Integer.parseInt(edtBloodGlucose.getText().toString()));
    }

    private void checkRequire() {
        if (data.getRecordDate() == null
                || edtBloodGlucose.getText().toString() == null
                || edtBloodGlucose.getText().toString().equals("")) {
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
                                getPresenter().deleteBloodSugar(data);
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
    public void onNewSuccess(final BloodSugarObject data) {
        SuccessDialog dialog = new SuccessDialog(getActivity());
        dialog.showDialog(getResources().getString(R.string.new_blood_sugar_success));
        dialog.setOnShowFinishListener(new SuccessDialog.OnShowFinishListener() {
            @Override
            public void onShowFinish() {
                int index = getActivity().getSupportFragmentManager().getBackStackEntryCount() - 2;
                if (index >= 0) {
                    FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                    String tag = backEntry.getName();
                    BloodSugarFragment fragment = (BloodSugarFragment) getFragmentManager().findFragmentByTag(tag);
                    fragment.setPatientMenuId(data.getPatientMenuId());
                }

                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }

    @Override
    public void onUpdateSuccess() {
        SuccessDialog dialog = new SuccessDialog(getActivity());
        dialog.showDialog(getResources().getString(R.string.update_blood_sugar_success));
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
        dialog.showDialog(getResources().getString(R.string.delete_blood_sugar_success));
        dialog.setOnShowFinishListener(new SuccessDialog.OnShowFinishListener() {
            @Override
            public void onShowFinish() {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }


}
