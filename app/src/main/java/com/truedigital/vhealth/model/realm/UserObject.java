package com.truedigital.vhealth.model.realm;

import io.realm.RealmObject;

/**
 * Created by nilecon on 2/15/2017 AD.
 */

public class UserObject extends RealmObject {

    private int userId;
    private String email;
    private int userType;
    private boolean isLogin;
    private String access_token;
    private String refresh_token;

    public UserObject() {
    }

    public UserObject(int userId, String email, int userType, String access_token, String refresh_token) {
        this.userId = userId;
        this.email = email;
        this.userType = userType;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
