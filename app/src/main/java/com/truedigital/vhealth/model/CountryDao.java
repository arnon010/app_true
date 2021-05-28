package com.truedigital.vhealth.model;

/**
 * Created by songkrit on 12/25/2017 AD.
 */

public class CountryDao {
    private int id;
    private int resIdFlag;
    private String Name;
    private String MobilePrefix;

    public CountryDao(int id, int resIdFlag, String name, String mobilePrefix) {
        this.id = id;
        this.resIdFlag = resIdFlag;
        Name = name;
        MobilePrefix = mobilePrefix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResIdFlag() {
        return resIdFlag;
    }

    public void setResIdFlag(int resIdFlag) {
        this.resIdFlag = resIdFlag;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobilePrefix() {
        return MobilePrefix;
    }

    public void setMobilePrefix(String mobilePrefix) {
        MobilePrefix = mobilePrefix;
    }
}
