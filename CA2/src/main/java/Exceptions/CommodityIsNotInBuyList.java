package Exceptions;

import static defines.Errors.ERROR_COMMODITY_IS_NOT_IN_BUY_LIST;

public class CommodityIsNotInBuyList extends Exception {
    public CommodityIsNotInBuyList() {
        super(ERROR_COMMODITY_IS_NOT_IN_BUY_LIST);
    }
}
