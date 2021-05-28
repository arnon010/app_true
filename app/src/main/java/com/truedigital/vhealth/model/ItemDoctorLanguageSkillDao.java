package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemDoctorLanguageSkillDao {

    @SerializedName("LanguageSkillCode") private String languageSkillCode; //"en-US"
    @SerializedName("Abbreviation") private String abbreviation; //"EN"

    public String getLanguageSkillCode() {
        return languageSkillCode;
    }

    public void setLanguageSkillCode(String languageSkillCode) {
        this.languageSkillCode = languageSkillCode;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
