package exceptions;

import static defines.Errors.MISSING_COMMENT_ID;

public class MissingCommentId extends Exception {
    public MissingCommentId() {
        super(MISSING_COMMENT_ID);
    }
}
