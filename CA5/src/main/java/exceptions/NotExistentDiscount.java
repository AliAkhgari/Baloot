package exceptions;

import static defines.Errors.NOT_EXISTENT_DISCOUNT;

public class NotExistentDiscount extends Exception {
    public NotExistentDiscount() {
        super(NOT_EXISTENT_DISCOUNT);
    }
}
