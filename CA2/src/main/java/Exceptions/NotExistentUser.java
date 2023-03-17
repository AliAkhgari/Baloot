package Exceptions;

import static defines.defines.ERROR_NOT_EXISTENT_USER;

public class NotExistentUser extends Exception {

    public NotExistentUser() {
        super(ERROR_NOT_EXISTENT_USER);
    }
}
