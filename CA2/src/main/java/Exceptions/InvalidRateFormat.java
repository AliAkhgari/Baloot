package Exceptions;

import static defines.Errors.ERROR_INVALID_RATE_FORMAT;

public class InvalidRateFormat extends Exception {
    public InvalidRateFormat() {
        super(ERROR_INVALID_RATE_FORMAT);
    }
}
