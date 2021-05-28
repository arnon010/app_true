package com.truedigital.vhealth.ui.setting.lang;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;

public class SettingLangFragment extends BaseMvpFragment<SettingLangFragmentInterface.Presenter>
        implements SettingLangFragmentInterface.View{

    private Button btnConfirm;
    private TextView btnLangTh;
    private TextView btnLangEn;

    public SettingLangFragment() {
        super();
    }

    public static SettingLangFragment newInstance() {
        SettingLangFragment fragment = new SettingLangFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public SettingLangFragmentInterface.Presenter createPresenter(){
        return SettingLangFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_setting_lang;
    }

    @Override
    public void bindView( View view ){
        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        btnLangTh = (TextView) view.findViewById(R.id.btn_lang_th);
        btnLangEn = (TextView) view.findViewById(R.id.btn_lang_en);
    }

    @Override
    public void setupInstance(){

    }

    @Override
    public void setupView(){
        showToolbar();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmClick();
            }
        });
        btnLangTh.setOnClickListener(LangThButtonClick());
        btnLangEn.setOnClickListener(LangEnButtonClick());

        updateView();
    }

    private void updateView() {
        String lang = CommonUtils.getLocalLanguage();
        Log.e("Lang default ",lang);
        if (lang.equals(AppConstants.LOCAL_LANG_THAI)) {
            updateLang(0);
        }
        else {
            updateLang(1);
        }
    }

    private View.OnClickListener LangThButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLang(0);
            }
        };
    }

    private View.OnClickListener LangEnButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLang(1);
            }
        };
    }

    private void updateLang(int lang) {
        if (lang == 0) {
            btnLangTh.setSelected(true);
            btnLangEn.setSelected(false);
        }
        else {
            btnLangTh.setSelected(false);
            btnLangEn.setSelected(true);
        }
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.setting_lang_title,true);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
    }

    @Override
    public void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );
    }

    @Override
    public void onConfirmClick() {
        //getPresenter().Logout();
        //getActivity().onBackPressed();

        String lang = "en";
        if (btnLangEn.isSelected()) lang = "en";
        if (btnLangTh.isSelected()) lang = "th";
        ((MainActivity) getActivity()).onSetLanguage(lang);
    }

    @Override
    public void openLogin() {
        ((MainActivity) getActivity()).openLogin();
    }


}

