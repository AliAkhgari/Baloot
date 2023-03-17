package Exceptions;

import static defines.Errors.ERROR_MISSING_COMMENT_ID;

public class MissingCommentId extends Exception {
    public MissingCommentId() {
        super(ERROR_MISSING_COMMENT_ID);
    }
}
