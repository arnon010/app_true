package com.truedigital.vhealth.utils;

import android.view.View;
import android.widget.ImageView;

import com.truedigital.vhealth.model.ItemDoctorLanguageSkillDao;

import java.util.List;

/**
 * Created by songkrit on 8/23/2018 AD.
 */

public class LanguageSkillsUtil {
    public static final String LANG_CODE_TH = "th-TH";
    public static final String LANG_CODE_EN = "en-US";

    private List<ItemDoctorLanguageSkillDao> listLanguageSkills;
    private List<String> listSkillsAppointment;
    private boolean isLangTh;
    private boolean isLangEn;
    private boolean isListAppointment;

    public LanguageSkillsUtil() {
    }

    public LanguageSkillsUtil(List<ItemDoctorLanguageSkillDao> listLanguageSkills) {
        this.listLanguageSkills = listLanguageSkills;
    }

    public LanguageSkillsUtil(List<String> listLanguageSkills, boolean isListAppointment) {
        this.listSkillsAppointment = listLanguageSkills;
        this.isListAppointment = isListAppointment;
    }

    public String getLanguageAppointment() {
        String buffer = "";
        if (listSkillsAppointment != null) {
            for (String languageSkills : listSkillsAppointment ) {
                if (languageSkills.equals("TH")) {
                    isLangTh = true;
                }
                if (languageSkills.equals("EN")) {
                    isLangEn = true;
                }
            }

            if (isLangTh) {
                buffer = "TH";
            }
            if (isLangEn) {
                if (buffer != "") {
                    buffer = buffer + " / ";
                }
                buffer = buffer + "EN";
            }
        }

        return buffer;
    }

    public String getLanguage() {
        String buffer = "";
        if (listLanguageSkills != null) {

            for (ItemDoctorLanguageSkillDao languageSkills : listLanguageSkills) {
                if (languageSkills.getLanguageSkillCode().equals(LANG_CODE_TH)) {
                    isLangTh = true;
                }
                if (languageSkills.getLanguageSkillCode().equals(LANG_CODE_EN)) {
                    isLangEn = true;
                }
            }

            if (isLangTh) {
                buffer = "TH";
            }
            if (isLangEn) {
                if (buffer != "") {
                    buffer = buffer + " / ";
                }
                buffer = buffer + "EN";
            }
        }

        return buffer;
    }

    public void show(ImageView imgLangTh, ImageView imgLangEn) {
        if (isListAppointment) {
            getLanguageAppointment();
        } else {
            getLanguage();
        }

        imgLangTh.setVisibility(isLangTh ? View.VISIBLE : View.GONE);
        imgLangEn.setVisibility(isLangEn ? View.VISIBLE : View.GONE);
    }
}
