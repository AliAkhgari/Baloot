package Exceptions;

import static defines.Errors.ERROR_NOT_EXISTENT_COMMODITY;

public class NotExistentCommodity extends Exception {
    public NotExistentCommodity() {
        super(ERROR_NOT_EXISTENT_COMMODITY);
    }
}
