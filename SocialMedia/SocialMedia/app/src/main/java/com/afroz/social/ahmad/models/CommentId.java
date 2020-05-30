package com.afroz.social.ahmad.models;

import androidx.annotation.NonNull;

/**
 * Created by afroz on 22/2/18.
 */

public class CommentId {

    public String commentId;

    public <T extends CommentId> T withId(@NonNull final String id) {
        this.commentId = id;
        return (T) this;
    }

}
