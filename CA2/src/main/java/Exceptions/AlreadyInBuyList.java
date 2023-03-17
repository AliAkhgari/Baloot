package Exceptions;

import static defines.Errors.ERROR_COMMODITY_IS_ALREADY_IN_BUY_LIST;

public class AlreadyInBuyList extends Exception {
    public AlreadyInBuyList() {
        super(ERROR_COMMODITY_IS_ALREADY_IN_BUY_LIST);
    }

}
