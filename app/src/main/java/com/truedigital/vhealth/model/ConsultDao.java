package com.truedigital.vhealth.model;

/**
 * Created by songkrit on 2/14/2018 AD.
 */

public class ConsultDao {
    private int id;
    private int resId;
    private int resIdActive;
    private String titleName;
    private boolean isAvailable;
    private boolean isChecked;
    private int resImageDrawable;
    private String PricePerMinuteFormat;
    private String typeCode;

    public final static String CHANNEL_CHAT = "CH";
    public final static String CHANNEL_VOICE = "VO";
    public final static String CHANNEL_VIDEO = "VI";

    public ConsultDao() {
    }


    public ConsultDao(int id, String typeCode,String titleName, int resImageDrawable, boolean isChecked, String pricePerMinuteFormat) {
        this.id = id;
        this.typeCode = typeCode;
        this.titleName = titleName;
        this.resImageDrawable = resImageDrawable;
        this.isChecked = isChecked;
        this.PricePerMinuteFormat = pricePerMinuteFormat;
    }

    public ConsultDao(int id, int resId, int resIdActive, String titleName, boolean isAvailable, boolean isChecked) {
        this.id = id;
        this.resId = resId;
        this.resIdActive = resIdActive;
        this.titleName = titleName;
        this.isAvailable = isAvailable;
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getResIdActive() {
        return resIdActive;
    }

    public void setResIdActive(int resIdActive) {
        this.resIdActive = resIdActive;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getResImageDrawable() {
        return resImageDrawable;
    }

    public void setResImageDrawable(int resImageDrawable) {
        this.resImageDrawable = resImageDrawable;
    }

    public String getPricePerMinuteFormat() {
        return PricePerMinuteFormat;
    }

    public void setPricePerMinuteFormat(String pricePerMinuteFormat) {
        PricePerMinuteFormat = pricePerMinuteFormat;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
