package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorSkillLangsDao extends NormalResponseObject {

    @SerializedName("LanguageSkillCode") private String LanguageSkillCode;

    public String getLanguageSkillCode() {
        return LanguageSkillCode;
    }

    public void setLanguageSkillCode(String languageSkillCode) {
        LanguageSkillCode = languageSkillCode;
    }
}
