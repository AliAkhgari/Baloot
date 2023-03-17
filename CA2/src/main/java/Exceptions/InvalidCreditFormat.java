package Exceptions;

import static defines.Errors.INVALID_CREDIT_FORMAT;

public class InvalidCreditFormat extends Exception {
    public InvalidCreditFormat() {
        super(INVALID_CREDIT_FORMAT);
    }

}
