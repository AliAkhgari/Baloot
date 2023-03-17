package Exceptions;

import static defines.Errors.ERROR_INSUFFICIENT_CREDIT;

public class InsufficientCredit extends Exception {
    public InsufficientCredit() {
        super(ERROR_INSUFFICIENT_CREDIT);
    }
}
