package exceptions;

import static defines.Errors.INVALID_VOTE_FORMAT;

public class InvalidVoteFormat extends Exception {
    public InvalidVoteFormat() {
        super(INVALID_VOTE_FORMAT);
    }

}
