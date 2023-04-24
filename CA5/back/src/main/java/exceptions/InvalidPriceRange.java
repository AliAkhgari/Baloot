package exceptions;

import static defines.Errors.INVALID_PRICE_RANGE;

public class InvalidPriceRange extends Exception {
    public InvalidPriceRange() {
        super(INVALID_PRICE_RANGE);
    }
}
