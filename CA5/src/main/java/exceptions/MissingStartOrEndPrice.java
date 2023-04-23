package exceptions;

import static defines.Errors.MISSING_START_OR_END_PRICE;

public class MissingStartOrEndPrice extends Exception {
    public MissingStartOrEndPrice() {
        super(MISSING_START_OR_END_PRICE);
    }

}
