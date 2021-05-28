package com.truedigital.vhealth.model;

/**
 * Created by nilecon on 3/6/2017 AD.
 */

public class ContactObject {

    private int contactTypeId;
    private int price;


    public ContactObject(int contactTypeId, int price) {
        this.contactTypeId = contactTypeId;
        this.price = price;
    }

    public int getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(int contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
