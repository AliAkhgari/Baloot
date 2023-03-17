package Exceptions;

import static defines.Errors.ERROR_NOT_EXISTENT_COMMENT;

public class NotExistentComment extends Exception {
    public NotExistentComment() {
        super(ERROR_NOT_EXISTENT_COMMENT);
    }
}
