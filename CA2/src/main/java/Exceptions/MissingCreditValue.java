package Exceptions;

import static defines.Errors.ERROR_MISSING_CREDIT_VALUE;

public class MissingCreditValue extends Exception {
    public MissingCreditValue() {
        super(ERROR_MISSING_CREDIT_VALUE);
    }
}
