package Exceptions;

import static defines.Errors.ERROR_MISSING_VOTE_VALUE;

public class MissingVoteValue extends Exception {
    public MissingVoteValue() {
        super(ERROR_MISSING_VOTE_VALUE);
    }

}