package com.truedigital.vhealth.model.realm;

import io.realm.RealmObject;

/**
 * Created by nilecon on 4/25/2017 AD.
 */

public class NotificationCountObject extends RealmObject{

    private int userId;
    private int count;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
