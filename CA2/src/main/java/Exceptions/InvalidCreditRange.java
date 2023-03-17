package Exceptions;

import static defines.defines.ERROR_INVALID_CREDIT_RANGE;

public class InvalidCreditRange extends Exception {
    public InvalidCreditRange() {
        super(ERROR_INVALID_CREDIT_RANGE);
    }
}
