package Exceptions;

import static defines.defines.ERROR_INVALID_RATE_FORMAT;

public class InvalidRateFormat extends Exception {
    public InvalidRateFormat() {
        super(ERROR_INVALID_RATE_FORMAT);
    }
}
