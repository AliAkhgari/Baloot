package exceptions;

import static defines.Errors.NOT_IN_STOCK;
public class NotInStock extends Exception {
    public NotInStock() {
        super(NOT_IN_STOCK);
    }
}
