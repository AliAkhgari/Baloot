package Exceptions;

import static defines.defines.ERROR_INVALID_RATE_RANGE;

public class InvalidRateRange extends Exception {
    public InvalidRateRange() {
        super(ERROR_INVALID_RATE_RANGE);
    }

}
