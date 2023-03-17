package Exceptions;

import static defines.Errors.MISSING_CATEGORY;

public class MissingCategory extends Exception {
    public MissingCategory() {
        super(MISSING_CATEGORY);
    }

}
