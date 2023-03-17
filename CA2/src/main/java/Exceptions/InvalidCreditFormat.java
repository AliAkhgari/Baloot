package Exceptions;

import static defines.Errors.ERROR_INVALID_CREDIT_FORMAT;

public class InvalidCreditFormat extends Exception {
    public InvalidCreditFormat() {
        super(ERROR_INVALID_CREDIT_FORMAT);
    }

}
