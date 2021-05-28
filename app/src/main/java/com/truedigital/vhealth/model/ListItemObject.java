package com.truedigital.vhealth.model;

public class ListItemObject {

    private String text;
    private String value;
    private Boolean isShowImageNext;

    public String getText() { return text;  }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() { return value;  }

    public void setValue(String value) { this.value = value; }

    public Boolean getIsShowImageNext() { return isShowImageNext;  }

    public void setIsShowImageNext(Boolean isShowImageNext) { this.isShowImageNext = isShowImageNext; }

}
