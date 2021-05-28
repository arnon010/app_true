package com.truedigital.vhealth.model;

/**
 * Created by nilecon on 3/31/2017 AD.
 */

public class OneSignalResponse {

    private String id;
    private int recipients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRecipients() {
        return recipients;
    }

    public void setRecipients(int recipients) {
        this.recipients = recipients;
    }
}
