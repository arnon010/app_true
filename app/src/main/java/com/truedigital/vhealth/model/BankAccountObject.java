package com.truedigital.vhealth.model;


import com.google.gson.annotations.SerializedName;

public class BankAccountObject {

    @SerializedName("Id")
    private int bankId;
    @SerializedName("Icon")
    private String icon;
    @SerializedName("BankName")
    private String bankName;
    @SerializedName("AccountNumber")
    private String accountNumber;

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
