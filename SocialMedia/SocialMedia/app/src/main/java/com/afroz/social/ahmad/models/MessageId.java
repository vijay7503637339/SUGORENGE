package com.afroz.social.ahmad.models;

import androidx.annotation.NonNull;

/**
 * Created by afroz on 22/2/18.
 */

public class MessageId {

    public String msgId;

    public <T extends MessageId> T withId(@NonNull final String id) {
        this.msgId = id;
        return (T) this;
    }

}
