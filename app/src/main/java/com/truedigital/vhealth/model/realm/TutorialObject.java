package com.truedigital.vhealth.model.realm;

import io.realm.RealmObject;

/**
 * Created by nilecon on 2/20/2017 AD.
 */

public class TutorialObject extends RealmObject {

    private boolean isFirstOpen;

    public boolean isFirstOpen() {
        return isFirstOpen;
    }

    public void setFirstOpen(boolean firstOpen) {
        isFirstOpen = firstOpen;
    }
}
