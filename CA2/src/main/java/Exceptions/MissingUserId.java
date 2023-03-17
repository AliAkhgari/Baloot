package Exceptions;

import static defines.defines.ERROR_MISSING_USER_ID;

public class MissingUserId extends Exception {
    public MissingUserId() {
        super(ERROR_MISSING_USER_ID);
    }
}