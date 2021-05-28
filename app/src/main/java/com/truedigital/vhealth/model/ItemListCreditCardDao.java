package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ItemListCreditCardDao {

    @SerializedName("CardId") private int CardId;
    @SerializedName("LastDigits") private String LastDigits;
    @SerializedName("Selected") private boolean selected;

    public int getCardId() {
        return CardId;
    }

    public void setCardId(int cardId) {
        CardId = cardId;
    }

    public String getLastDigits() {
        return LastDigits;
    }

    public void setLastDigits(String lastDigits) {
        LastDigits = lastDigits;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
