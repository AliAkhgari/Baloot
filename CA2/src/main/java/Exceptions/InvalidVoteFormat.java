package Exceptions;

import static defines.Errors.ERROR_INVALID_VOTE_FORMAT;

public class InvalidVoteFormat extends Exception {
    public InvalidVoteFormat() {
        super(ERROR_INVALID_VOTE_FORMAT);
    }

}
