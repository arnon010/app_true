package com.truedigital.vhealth.model;

public class ItemCreditCardDao {

    private String CreditCardNumber;
    private String CreditCardName;
    private String CreditCardExpired;
    private int CreditCardExpiredMonth;
    private int CreditCardExpiredYear;
    private String CreditCardCVV;

    public ItemCreditCardDao() {

    }

    public ItemCreditCardDao(String creditCardNumber, String creditCardName, int creditCardExpiredMonth, int creditCardExpiredYear, String creditCardCVV) {
        CreditCardNumber = creditCardNumber;
        CreditCardName = creditCardName;
        CreditCardExpiredMonth = creditCardExpiredMonth;
        CreditCardExpiredYear = creditCardExpiredYear;
        CreditCardCVV = creditCardCVV;
    }

    public String getCreditCardNumber() {
        return CreditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        CreditCardNumber = creditCardNumber;
    }

    public String getCreditCardName() {
        return CreditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        CreditCardName = creditCardName;
    }

    public String getCreditCardExpired() {
        return CreditCardExpired;
    }

    public void setCreditCardExpired(String creditCardExpired) {
        CreditCardExpired = creditCardExpired;
    }

    public int getCreditCardExpiredMonth() {
        return CreditCardExpiredMonth;
    }

    public void setCreditCardExpiredMonth(int creditCardExpiredMonth) {
        CreditCardExpiredMonth = creditCardExpiredMonth;
    }

    public int getCreditCardExpiredYear() {
        return CreditCardExpiredYear;
    }

    public void setCreditCardExpiredYear(int creditCardExpiredYear) {
        CreditCardExpiredYear = creditCardExpiredYear;
    }

    public String getCreditCardCVV() {
        return CreditCardCVV;
    }

    public void setCreditCardCVV(String creditCardCVV) {
        CreditCardCVV = creditCardCVV;
    }
}
