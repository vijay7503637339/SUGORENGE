package com.afroz.social.ahmad.models;

import androidx.annotation.NonNull;

/**
 * Created by afroz on 22/2/18.
 */

public class PostId {

    public String postId;

    public <T extends PostId> T withId(@NonNull final String id) {
        this.postId = id;
        return (T) this;
    }

}
