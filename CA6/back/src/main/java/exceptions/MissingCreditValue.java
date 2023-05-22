package exceptions;

import static defines.Errors.MISSING_CREDIT_VALUE;

public class MissingCreditValue extends Exception {
    public MissingCreditValue() {
        super(MISSING_CREDIT_VALUE);
    }
}
