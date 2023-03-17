package Exceptions;

import static defines.Errors.ERROR_INVALID_PRICE_RANGE;

public class InvalidPriceRange extends Exception {
    public InvalidPriceRange() {
        super(ERROR_INVALID_PRICE_RANGE);
    }
}
