package com.truedigital.vhealth.model.realm;

import io.realm.RealmObject;

/**
 * Created by nilecon on 5/12/2017 AD.
 */

public class LanguageObject extends RealmObject {

    private String language;

    public LanguageObject() {
    }

    public LanguageObject(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
