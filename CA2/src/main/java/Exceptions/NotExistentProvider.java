package Exceptions;

import static defines.defines.ERROR_NOT_EXISTENT_PROVIDER;

public class NotExistentProvider extends Exception {
    public NotExistentProvider() {
        super(ERROR_NOT_EXISTENT_PROVIDER);
    }
}
