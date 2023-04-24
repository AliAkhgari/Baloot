package exceptions;

import static defines.Errors.EXPIRED_DISCOUNT;

public class ExpiredDiscount extends Exception {
    public ExpiredDiscount() {
        super(EXPIRED_DISCOUNT);
    }
}
