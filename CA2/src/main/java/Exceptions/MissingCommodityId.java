package Exceptions;

import static defines.Errors.ERROR_MISSING_COMMODITY_ID;

public class MissingCommodityId extends Exception {
    public MissingCommodityId() {
        super(ERROR_MISSING_COMMODITY_ID);
    }
}
