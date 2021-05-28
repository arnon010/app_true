package com.truedigital.vhealth.manager.bus;

/**
 * Created by songkrit on 6/21/2018 AD.
 */

public class MessageEvent {

    public static final String MSG_NOTIFICATION = "MSG_NOTIFICATION";
    public static final String MSG_TIME_TO_CALL = "MSG_TIME_TO_CALL";
    public static final String MSG_MEETING_TIME_OUT = "MSG_MEETING_TIME_OUT";
    public final String message;

    public MessageEvent(String message) {
            this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
