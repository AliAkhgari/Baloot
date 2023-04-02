package exceptions;

import static defines.Errors.INVALID_RATE_FORMAT;

public class InvalidRateFormat extends Exception {
    public InvalidRateFormat() {
        super(INVALID_RATE_FORMAT);
    }
}
