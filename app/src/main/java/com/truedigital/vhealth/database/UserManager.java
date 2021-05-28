package com.truedigital.vhealth.database;

import com.truedigital.vhealth.model.realm.UserObject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by nilecon on 2/15/2017 AD.
 */

public class UserManager {

    private Realm realm;

    public UserManager() {
        realm = Realm.getDefaultInstance();
    }

    public String getAccess_Token(final int userId) {
        RealmQuery<UserObject> query = realm.where(UserObject.class);
        RealmResults<UserObject> result = query
                .equalTo("userId", userId)
                .findAll();
        if (result.size() == 0) {
            return "";
        } else {
            return result.get(0).getAccess_token();
        }
    }

    public String getRefresh_Token(final int userId) {
        RealmQuery<UserObject> query = realm.where(UserObject.class);
        RealmResults<UserObject> result = query
                .equalTo("userId", userId)
                .findAll();
        if (result.size() == 0) {
            return "";
        } else {
            return result.get(0).getRefresh_token();
        }
    }

    /*
    public void login(final String access_token) {
        realm.beginTransaction();
        UserObject object = realm.createObject(UserObject.class);
        object.setAccess_token(access_token);
        object.setLogin(true);
        realm.commitTransaction();
    }
    */

    public void login(final UserObject userObject) {
        //clearStatusLogin();
        deleteAll();
        if (!isUserId(userObject.getUserId())) {
            realm.beginTransaction();
            UserObject object = realm.createObject(UserObject.class);
            object.setUserId(userObject.getUserId());
            object.setEmail(userObject.getEmail());
            object.setUserType(userObject.getUserType());
            object.setLogin(true);
            object.setAccess_token(userObject.getAccess_token());
            object.setRefresh_token(userObject.getRefresh_token());
            realm.commitTransaction();
        } else {
            update(userObject);
        }
    }

    public void logout(final int userId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserObject query = realm.where(UserObject.class)
                        .equalTo("userId", userId)
                        .findFirst();
                query.setAccess_token("");
                query.setLogin(false);
            }
        });
    }

    public void update(final UserObject userObject) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserObject query = realm.where(UserObject.class)
                        .equalTo("userId", userObject.getUserId())
                        .findFirst();
                query.setEmail(userObject.getEmail());
                query.setLogin(true);
            }
        });
    }

    public void updateEmail(final int userId, final String newEmail) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserObject query = realm.where(UserObject.class)
                        .equalTo("userId", userId)
                        .findFirst();
                query.setEmail(newEmail);
            }
        });
    }

    public void deleteAll() {
        // obtain the results of a query
        final RealmResults<UserObject> results = realm.where(UserObject.class)
                .findAll();

        // All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Delete all matches
                results.deleteAllFromRealm();
            }
        });
    }

    public void delete(int userId) {
        // obtain the results of a query
        final RealmResults<UserObject> results = realm.where(UserObject.class)
                .equalTo("userId", userId)
                .findAll();

        // All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // remove single match
                results.deleteFirstFromRealm();
            }
        });
    }

    public RealmResults<UserObject> queryAll() {
        RealmQuery<UserObject> query = realm.where(UserObject.class);
        RealmResults<UserObject> result = query
                .findAll();
        return result;
    }

    public boolean isUserId(int userId) {
        RealmQuery<UserObject> query = realm.where(UserObject.class);
        RealmResults<UserObject> result = query
                .equalTo("userId", userId)
                .findAll();
        if (result.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isLogin() {
        RealmQuery<UserObject> query = realm.where(UserObject.class);
        RealmResults<UserObject> result = query
                .equalTo("isLogin", true)
                .findAll();
        if (result.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public int getUserType() {
        RealmQuery<UserObject> query = realm.where(UserObject.class);
        RealmResults<UserObject> result = query
                .equalTo("isLogin", true)
                .findAll();
        if (result.size() == 0) {
            return 0;
        } else {
            return result.get(0).getUserType();
        }
    }

    public int getCurrentUserId() {
        RealmQuery<UserObject> query = realm.where(UserObject.class);
        RealmResults<UserObject> result = query
                .equalTo("isLogin", true)
                .findAll();
        if (result.size() == 0) {
            return 0;
        } else {
            return result.get(0).getUserId();
        }
    }

    private void clearStatusLogin() {
        if (isLogin()) {
            RealmQuery<UserObject> query = realm.where(UserObject.class);
            RealmResults<UserObject> result = query
                    .equalTo("isLogin", true)
                    .findAll();

            for (int i = 0; i < result.size(); i++) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        UserObject query = realm.where(UserObject.class)
                                .equalTo("isLogin", true)
                                .findFirst();
                        query.setLogin(false);
                        query.setAccess_token("");
                    }
                });
            }
        }
    }
}
